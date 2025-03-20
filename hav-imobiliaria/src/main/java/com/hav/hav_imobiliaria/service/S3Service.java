package com.hav.hav_imobiliaria.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;

@Service
@AllArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;

    public String uploadFile(MultipartFile file) {
        try {
            String key = UUID.randomUUID().toString();

            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

            return key;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar arquivo para o S3", e);
        }
    }
}
