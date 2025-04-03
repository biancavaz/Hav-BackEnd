package com.hav.hav_imobiliaria.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class AwsConfig {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String awsRegion;

    AwsBasicCredentials awsCreds = AwsBasicCredentials.create("",
            "");

    @Bean
    public S3Client s3Client() {
        String endpointUrl = "https://s3.us-east-2.amazonaws.com";

        return S3Client.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .endpointOverride(URI.create(endpointUrl))
                .build();
    }

    @Bean
    public String bucketName() {
        return bucketName;
    }
}
