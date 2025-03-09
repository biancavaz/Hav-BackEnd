package com.hav.hav_imobiliaria.model.DTO.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddressPutRequestDTO(
        String cep,
        String street,
        String neighborhood,
        String city,
        String state,
        Integer propertyNumber,
        String complement) {
}
