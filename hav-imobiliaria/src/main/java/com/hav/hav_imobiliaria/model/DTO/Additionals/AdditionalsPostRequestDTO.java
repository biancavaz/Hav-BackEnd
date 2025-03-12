package com.hav.hav_imobiliaria.model.DTO.Additionals;

import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import jakarta.validation.constraints.NotBlank;

public record AdditionalsPostRequestDTO(
        @NotBlank(message = "Nome não pode estar em branco")
        String name
) {
    public Additionals convert() {
        return Additionals.builder().name(name).build();
    }
}
