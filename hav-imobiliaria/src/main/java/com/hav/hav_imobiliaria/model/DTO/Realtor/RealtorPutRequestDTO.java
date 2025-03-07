package com.hav.hav_imobiliaria.model.DTO.Realtor;


import com.hav.hav_imobiliaria.model.entity.User.Realtor;
import jakarta.validation.constraints.NotBlank;

public record RealtorPutRequestDTO(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String creci) {


    public Realtor convert(){
        return Realtor.builder()
                .email(email)
                .password(password)
                .creci(creci)
                .build();
    }
}
