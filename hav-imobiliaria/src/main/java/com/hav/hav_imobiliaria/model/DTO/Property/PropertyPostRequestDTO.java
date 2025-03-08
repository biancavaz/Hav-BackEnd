package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record PropertyPostRequestDTO(
        String propertyCode,
        @NotBlank String title,
        @NotBlank String propertyDescription,
        @NotBlank String propertyType,
        @NotBlank String purpose,
        @NotBlank String propertyStatus,
        @Positive @NotNull Double area,
        @Positive @NotNull Double price,
        @PositiveOrZero Double promotionalPrice,
        @NotNull Boolean highlight,
        @NotBlank String propertyCategory,
        @NotNull AddressPostRequestDTO address,
        @NotNull TaxesPostRequestDTO taxes,
        @NotNull PropertyFeaturePostRequestDTO propertyFeatures,
        @NotNull List<Integer> additionals,
        List<Integer> realtors,
        Integer proprietor
) {
    public Property convert() {
        return Property.builder().title(title).
                propertyDescription(propertyDescription).
                propertyType(propertyType).propertyStatus(propertyStatus).area(area).price(price).
                promotionalPrice(promotionalPrice).purpose(purpose).highlight(highlight).
                propertyCategory(propertyCategory).
                address(address.convert()).taxes(taxes.convert()).
                propertyFeatures(propertyFeatures.convert())
                .build();
    }
}
