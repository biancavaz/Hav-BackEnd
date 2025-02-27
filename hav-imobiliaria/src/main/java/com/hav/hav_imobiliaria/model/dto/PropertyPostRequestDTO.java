package com.hav.hav_imobiliaria.model.dto;

import com.hav.hav_imobiliaria.model.entity.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record PropertyPostRequestDTO(
        @NotBlank String title,
        @NotBlank String propertyDescription,
        @NotBlank String propertyType,
        @NotBlank String propertyStatus,
        @Positive @NotNull Double area,
        @Positive @NotNull Double price,
        @PositiveOrZero Double promotionalPrice,
        @NotNull Boolean highlight,
        @NotBlank String propertyCategory,
        @NotNull Address address,
        @NotNull Taxes taxes,
        @NotNull PropertyFeature propertyFeatures,
        @NotNull List<Integer> additionalsId
) {
    public Property convert() {
        return Property.builder().title(title).propertyDescription(propertyDescription).
                propertyType(propertyType).propertyStatus(propertyStatus).area(area).price(price).
                promotionalPrice(promotionalPrice).highlight(highlight).propertyCategory(propertyCategory).
                address(address).taxes(taxes).propertyFeatures(propertyFeatures).build();
    }
}
