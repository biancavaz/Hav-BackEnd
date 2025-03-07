package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPostRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PropertyGetResponseDTO(
        @NotBlank String propertyCode,
        @NotBlank String propertyType,
        @NotBlank String propertyStatus,
        @NotBlank String purpose,
        @NotNull List<RealtorGetResponseDTO> realtorList
        @NotNull ProprietorGetResponseDTO proprietor
        ) {
}
