
package com.hav.hav_imobiliaria.model.DTO.Address;

import com.hav.hav_imobiliaria.model.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record AddressPostRequestDTO(
        @NotBlank(message = "CEP não pode estar em branco")
        @Pattern(regexp = "\\d{8}$", message = "CEP inválido")
        String cep,
        @NotBlank(message = "Rua não pode estar em branco")
        String street,
        @NotBlank(message = "Bairro não pode estar em branco")
        String neighborhood,
        @NotBlank(message = "Cidade não pode estar em branco")
        String city,
        @NotBlank(message = "Estado não pode estar em branco")
        String state,
        @Positive(message = "Número deve ser positivo")
        @NotNull(message = "Número inválido")
        Integer propertyNumber,
        String complement
) {
    public Address convert() {
        return Address.builder().cep(cep).street(street).
                neighborhood(neighborhood).city(city).state(state).
                propertyNumber(propertyNumber).complement(complement).build();
    }
}
