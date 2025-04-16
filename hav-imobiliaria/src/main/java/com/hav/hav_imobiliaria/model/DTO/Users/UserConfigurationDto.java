package com.hav.hav_imobiliaria.model.DTO.Users;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Address;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserConfigurationDto {
    private String s3key;
    private String name;
    private String email;
    private String celphone;
    private String phoneNumber;
    private String cpf;
    private AddressGetResponseDTO address;
    private String userType;

    public void setS3key(String s3key) {
        this.s3key = s3key;
    }
}
