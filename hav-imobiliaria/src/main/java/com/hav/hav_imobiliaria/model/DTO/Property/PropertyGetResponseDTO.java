package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
import com.hav.hav_imobiliaria.model.entity.Realtor;
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
