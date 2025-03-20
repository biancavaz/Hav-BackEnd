package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Image.ImageResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.ImageProperty;
import com.hav.hav_imobiliaria.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImageController {

    private ImageService imageService;

    @PostMapping("/properties/{propertyId}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadImages(@PathVariable Integer propertyId, @RequestParam("images") List<MultipartFile> images) {
        imageService.uploadImages(propertyId, images);
    }

    @GetMapping("/properties/{propertyId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ImageResponseDTO> getImages(@PathVariable Integer propertyId) {
        List<ImageProperty> imageProperties = imageService.getImagesByProperty(propertyId);
        return imageProperties.stream()
                .map(image -> new ImageResponseDTO(image.getId(), image.getS3Key()))
                .collect(Collectors.toList());
    }
}
