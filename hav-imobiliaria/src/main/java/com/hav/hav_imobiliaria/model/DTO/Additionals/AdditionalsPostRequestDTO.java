package com.hav.hav_imobiliaria.model.DTO.Additionals;

import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AdditionalsPostRequestDTO(
        @NotBlank(message = "Nome não pode estar em branco")
        @Pattern(regexp = "\\d{4}", message = "Nome deve conter no mínimo 4 caracteres")
        String name
) {
    public Additionals convert() {
        return Additionals.builder().name(name).build();
    }
}
