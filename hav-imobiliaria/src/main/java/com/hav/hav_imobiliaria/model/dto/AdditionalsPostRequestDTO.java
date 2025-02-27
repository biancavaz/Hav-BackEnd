package com.hav.hav_imobiliaria.model.dto;

import com.hav.hav_imobiliaria.model.entity.Additionals;
import com.hav.hav_imobiliaria.model.entity.Property;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AdditionalsPostRequestDTO(
        @NotBlank String name
) {
    public Additionals convert() {
        return Additionals.builder().name(name).build();
    }
}
