package com.hav.hav_imobiliaria.model.DTO.Property;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PropertyGetResponseDTO(
        @NotBlank String propertyCode,
        @NotBlank String propertyType,
        @NotBlank String propertyStatus,
        @NotBlank String purpose,
        List<Realtor> realtors,
        CustomerOwner owner
) {
}
