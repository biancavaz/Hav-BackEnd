package com.hav.hav_imobiliaria.model.DTO.Address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddressPutRequestDTO {
    @NotBlank
    String cep;
    @NotBlank
    String street;
    @NotBlank
    String neighborhood;
    @NotBlank
    String city;
    @NotBlank
    String state;
    @NotNull
    @Positive
    Integer propertyNumber;
    @NotBlank
    String complement;
}
