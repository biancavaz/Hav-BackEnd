package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Image.ImageResponseDTO;
import com.hav.hav_imobiliaria.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

//    @GetMapping("/property/{imageId}")
//    public ResponseEntity<byte[]> getPropertyImage(@PathVariable Integer imageId) {
//        byte[] imageData = imageService.getPropertyImage(imageId);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG) // ou IMAGE_PNG dependendo do seu arquivo
//                .body(imageData);
//    }

//    @PostMapping("/property/images")
//    public ResponseEntity<List<ImageResponseDTO>> getPropertyImages(@RequestBody List<Integer> imageIds) {
//        List<ImageResponseDTO> images = imageService.getPropertyImages(imageIds);
//        return ResponseEntity.ok(images);
//    }

    @PostMapping("/property/images")
    public ResponseEntity<List<byte[]>> getPropertyImages(@RequestBody List<Integer> imageIds) {
        List<byte[]> images = imageService.getPropertyImages(imageIds);
        return ResponseEntity.ok(images);
    }



    @GetMapping("/user/{imageId}")
    public ResponseEntity<byte[]> getCustomerImage(@PathVariable Integer imageId) {
        byte[] imageData = imageService.getUserImage(imageId);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE) // ou IMAGE_PNG_VALUE
                .body(imageData);
    }
}

