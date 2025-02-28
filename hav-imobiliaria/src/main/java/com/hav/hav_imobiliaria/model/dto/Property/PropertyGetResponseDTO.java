package com.hav.hav_imobiliaria.model.dto.Property;

import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
import com.hav.hav_imobiliaria.model.entity.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PropertyGetResponseDTO(
        @NotBlank String propertyCode,
        @NotBlank String propertyType,
        @NotBlank String propertyStatus,
        @NotBlank String purpose,
        @NotNull List<Realtor> realtors,
        @NotNull CustomerOwner owner
) {
}
