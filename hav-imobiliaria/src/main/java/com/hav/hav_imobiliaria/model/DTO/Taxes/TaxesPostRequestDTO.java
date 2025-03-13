package com.hav.hav_imobiliaria.model.DTO.Taxes;

import com.hav.hav_imobiliaria.model.entity.Properties.Taxes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TaxesPostRequestDTO(
        @PositiveOrZero(message = "Condomínio deve ser positivo ou zero")
        @NotNull(message = "Condomínio não pode estar em branco")
        Double condominiumFee,
        @PositiveOrZero(message = "IPTU deve ser positivo ou zero")
        @NotNull(message = "IPTU não pode estar em branco")
        Double iptu
) {

}
