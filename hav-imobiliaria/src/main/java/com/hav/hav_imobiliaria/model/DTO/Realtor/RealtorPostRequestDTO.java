package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.entity.Realtor;
import jakarta.validation.constraints.NotBlank;

public record RealtorPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String creci
) {

    public Realtor convert(){
        return Realtor.builder()
                .name(name)
                .email(email)
                .password(password)
                .creci(creci)
                .build();
    }
}
