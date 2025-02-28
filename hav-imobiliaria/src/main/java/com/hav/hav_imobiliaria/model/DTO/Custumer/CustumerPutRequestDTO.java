package com.hav.hav_imobiliaria.model.DTO.Custumer;

import com.hav.hav_imobiliaria.model.entity.Users.Custumer;
import jakarta.validation.constraints.NotBlank;

public record CustumerPutRequestDTO(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String celphone) {

    public Custumer convert(){
        return Custumer.builder()
                .email(email)
                .password(password)
                .celphone(celphone)
                .build();
    }
}
