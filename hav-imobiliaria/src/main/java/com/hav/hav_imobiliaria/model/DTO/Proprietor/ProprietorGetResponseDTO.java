package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProprietorGetResponseDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotNull String celphone
) {
}
