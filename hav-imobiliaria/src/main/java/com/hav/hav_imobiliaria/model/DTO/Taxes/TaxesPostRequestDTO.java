package com.hav.hav_imobiliaria.model.DTO.Taxes;

import com.hav.hav_imobiliaria.model.entity.Taxes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TaxesPostRequestDTO(
        @PositiveOrZero @NotNull Double condominiumFee,
        @PositiveOrZero @NotNull Double iptu) {

    public Taxes convert() {
        return Taxes.builder()
                .condominiumFee(condominiumFee)
                .iptu(iptu)
                .build();
    }
}
