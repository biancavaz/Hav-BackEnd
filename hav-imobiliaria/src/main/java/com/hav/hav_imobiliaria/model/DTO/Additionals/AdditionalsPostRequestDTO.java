package com.hav.hav_imobiliaria.model.DTO.Additionals;

import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AdditionalsPostRequestDTO(
        @NotBlank(message = "O nome não pode estar em branco")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}( [A-Za-zÀ-ÿ]{2,})+$", message = "Nome inválido")
        String name
) {
    public Additionals convert() {
        return Additionals.builder().name(name).build();
    }
}
