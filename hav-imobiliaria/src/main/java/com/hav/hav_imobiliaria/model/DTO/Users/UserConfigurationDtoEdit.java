package com.hav.hav_imobiliaria.model.DTO.Users;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressGetResponseDTO;

import lombok.Data;

@Data
public class UserConfigurationDtoEdit {
    private String deleteds3key;
    private String name;
    private String email;
    private String celphone;
    private String phoneNumber;
    private AddressGetResponseDTO address;
    

}
