
package com.hav.hav_imobiliaria.model.DTO.Address;

import com.hav.hav_imobiliaria.model.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddressPostRequestDTO(
        @NotBlank String cep,
        @NotBlank String street,
        @NotBlank String neighborhood,
        @NotBlank String city,
        @NotBlank String state,
        @Positive @NotNull Integer propertyNumber,
        String complement
) {
    public Address convert() {
        return Address.builder().cep(cep).street(street).
                neighborhood(neighborhood).city(city).state(state).
                propertyNumber(propertyNumber).complement(complement).build();
    }
}
