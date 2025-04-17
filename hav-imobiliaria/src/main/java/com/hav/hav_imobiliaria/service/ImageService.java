package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Image.ImageResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.ImageProperty;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.ImageUser;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ImagePropertyRepository imagePropertyRepository;
    private final ImageUserRepository imageUserRepository;
    private final S3Service s3Service;


    //    public void uploadPropertyImages(Integer propertyId, List<MultipartFile> images) {
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));
//
//        List<ImageProperty> imageEntities = images.stream()
//                .map(image -> new ImageProperty(s3Service.uploadFile(image), property))
//                .collect(Collectors.toList());
//
//        imagePropertyRepository.saveAll(imageEntities);
//    }
    public void uploadPropertyImages(Integer propertyId, List<MultipartFile> images) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        List<ImageProperty> imageEntities = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            MultipartFile image = images.get(i);
            String s3Key = s3Service.uploadFile(image);

            ImageProperty imageProperty = new ImageProperty(s3Key, property);
            imageProperty.setMainImage(i == 0); // A primeira imagem será a principal

            imageEntities.add(imageProperty);
        }

        imagePropertyRepository.saveAll(imageEntities);
    }


    public void uploadUserImage(Integer userId, MultipartFile image) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se o usuário já possui uma imagem
        Optional<ImageUser> existingImageOpt = imageUserRepository.findByUser_Id(userId);

        String s3Key = s3Service.uploadFile(image);

        if (existingImageOpt.isPresent()) {
            // Atualiza a imagem existente
            ImageUser existingImage = existingImageOpt.get();
            existingImage.setS3Key(s3Key);
            imageUserRepository.save(existingImage);
        } else {
            // Cria uma nova imagem
            ImageUser newImage = new ImageUser(s3Key, user);
            imageUserRepository.save(newImage);
        }
    }

    public void deletePropertyImages(Integer propertyId) {

        List<ImageProperty> images = imagePropertyRepository.findAllByProperty_Id(propertyId);

        if (images.isEmpty()) {
            return;
        }

        for (ImageProperty image : images) {
            s3Service.deleteFile(image.getS3Key());
        }

        imagePropertyRepository.deleteAll(images);
    }

    public void deleteUserImage(Integer imageId) {
        ImageUser image = imageUserRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Imagem de usuário não encontrada."));

        s3Service.deleteFile(image.getS3Key());
        imageUserRepository.delete(image);
    }

    public List<byte[]> getPropertyImages(List<Integer> imageIds) {
        return imageIds.stream()
                .map(id -> imagePropertyRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Imagem com ID " + id + " não encontrada.")))
                .map(image -> s3Service.downloadFile(image.getS3Key()))
                .collect(Collectors.toList());
    }

//    public List<ImageResponseDTO> getPropertyImages(List<Integer> imageIds) {
//        return imageIds.stream()
//                .map(id -> imagePropertyRepository.findById(id)
//                        .orElseThrow(() -> new RuntimeException("Imagem com ID " + id + " não encontrada.")))
//                .map(image -> {
//                    byte[] fileData = s3Service.downloadFile(image.getS3Key());
//                    String base64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(fileData);
//                    return new ImageResponseDTO(image.getId(), base64);
//                })
//                .collect(Collectors.toList());
//    }


    public byte[] getUserImage(Integer imageId) {
        ImageUser image = imageUserRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Imagem não encontrada."));

        return s3Service.downloadFile(image.getS3Key());
    }
}
