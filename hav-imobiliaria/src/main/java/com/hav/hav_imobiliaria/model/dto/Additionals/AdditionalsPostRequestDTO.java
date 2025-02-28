package com.hav.hav_imobiliaria.model.dto.Additionals;

import com.hav.hav_imobiliaria.model.entity.Additionals;
import jakarta.validation.constraints.NotBlank;

public record AdditionalsPostRequestDTO(
        @NotBlank String name
) {
    public Additionals convert() {
        return Additionals.builder().name(name).build();
    }
}
