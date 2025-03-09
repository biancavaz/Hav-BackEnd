package com.hav.hav_imobiliaria.model.DTO.Realtor;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record RealtorGetResponseDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String cpf,
        @NotBlank String celphone,
        String phoneNumber,
        @NotBlank Date birthDate,
        @NotBlank String creci) {

}
