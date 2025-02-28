package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.DTO.User.UserPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RealtorPostRequestDTO(
        @NotNull UserPostRequestDTO userDTO,
        @NotBlank String creci
) {

    public Realtor convert() {
        return Realtor.builder()
                .creci(creci)
                .user(userDTO.convert())
                .build();
    }
}
