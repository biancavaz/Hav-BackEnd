package com.hav.hav_imobiliaria.model.DTO.CustumerOwner;

import com.hav.hav_imobiliaria.model.entity.CustumerOwner;
import jakarta.validation.constraints.NotBlank;

public record CustumerOwnerPutRequestDTO(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String celphone) {

    public CustumerOwner convert(){
        return CustumerOwner.builder()
                .email(email)
                .password(password)
                .celphone(celphone)
                .build();
    }
}
