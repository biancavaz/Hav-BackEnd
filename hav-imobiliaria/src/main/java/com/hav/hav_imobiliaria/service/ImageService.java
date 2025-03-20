package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.Properties.ImageProperty;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.ImageUser;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ImagePropertyRepository imagePropertyRepository;
    private final ImageUserRepository imageUserRepository;
    private final S3Service s3Service;


    public void uploadPropertyImages(Integer propertyId, List<MultipartFile> images) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        List<ImageProperty> imageEntities = images.stream()
                .map(image -> new ImageProperty(s3Service.uploadFile(image), property))
                .collect(Collectors.toList());

        imagePropertyRepository.saveAll(imageEntities);
    }

    public void uploadUserImage(Integer userId, MultipartFile image) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        ImageUser userImage = new ImageUser(s3Service.uploadFile(image), user);

        imageUserRepository.save(userImage);
    }

//    public List<ImageProperty> getImagesByProperty(Integer propertyId) {
//        return imageRepository.findByProperty_Id(propertyId);
//    }
}
