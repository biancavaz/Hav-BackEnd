package com.hav.hav_imobiliaria.model.DTO;

import com.hav.hav_imobiliaria.model.entity.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
