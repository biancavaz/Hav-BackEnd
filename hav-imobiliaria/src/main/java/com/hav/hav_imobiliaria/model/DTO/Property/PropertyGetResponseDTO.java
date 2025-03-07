package com.hav.hav_imobiliaria.model.DTO.Property;

import jakarta.validation.constraints.NotBlank;

public record PropertyGetResponseDTO(
        @NotBlank String propertyCode,
        @NotBlank String propertyType,
        @NotBlank String propertyStatus,
        @NotBlank String purpose
) {
}
