package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.entity.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record RealtorGetResponseDTO(
        @NotBlank Integer id,
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String cpf,
        @NotBlank String celphone,
        String phoneNumber,
        @NotBlank Date birthDate,
        @NotBlank String creci) {

}
