package com.hav.hav_imobiliaria.model.dto;

import com.hav.hav_imobiliaria.model.entity.PropertyFeature;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PropertyFeaturePostRequestDTO(
        @NotNull Boolean allowsPet,
        @PositiveOrZero @NotNull Integer bedRoom,
        @PositiveOrZero @NotNull Integer livingRoom,
        @PositiveOrZero @NotNull Integer bathRoom,
        @PositiveOrZero @NotNull Integer garageSpace,
        @NotNull Boolean isFurnished
) {
    public PropertyFeature convert() {
        return PropertyFeature.builder().allowsPet(allowsPet).bedRoom(bedRoom).livingRoom(livingRoom).
                bathRoom(bathRoom).garageSpace(garageSpace).isFurnished(isFurnished).build();
    }
}
