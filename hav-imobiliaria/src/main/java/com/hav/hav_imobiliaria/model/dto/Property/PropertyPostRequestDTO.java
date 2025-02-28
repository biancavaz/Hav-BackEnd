package com.hav.hav_imobiliaria.model.dto.Property;

import com.hav.hav_imobiliaria.model.dto.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.dto.PropertyFeature.PropertyFeaturePostRequestDTO;
import com.hav.hav_imobiliaria.model.dto.Taxes.TaxesPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.*;
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
        @NotNull AddressPostRequestDTO addressDTO,
        @NotNull TaxesPostRequestDTO taxesDTO,
        @NotNull PropertyFeaturePostRequestDTO propertyFeaturesDTO,
        @Positive @NotNull List<Integer> additionalsId,
        @Positive @NotNull List<Integer> realtorsId,
        @Positive @NotNull Integer ownerId
) {
    public Property convert() {
        return Property.builder().propertyCode(null).title(title).
                propertyDescription(propertyDescription).
                propertyType(propertyType).propertyStatus(propertyStatus).area(area).price(price).
                promotionalPrice(promotionalPrice).purpose(purpose).highlight(highlight).
                propertyCategory(propertyCategory).
                address(addressDTO.convert()).taxes(taxesDTO.convert()).
                propertyFeatures(propertyFeaturesDTO.convert()).build();
    }
}
