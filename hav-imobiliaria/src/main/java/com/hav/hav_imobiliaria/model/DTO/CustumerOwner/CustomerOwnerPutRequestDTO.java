package com.hav.hav_imobiliaria.model.DTO.CustumerOwner;

import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
import jakarta.validation.constraints.NotBlank;

public record CustomerOwnerPutRequestDTO(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String celphone) {

    public CustomerOwner convert(){
        return CustomerOwner.builder()
                .email(email)
                .password(password)
                .celphone(celphone)
                .build();
    }
}
