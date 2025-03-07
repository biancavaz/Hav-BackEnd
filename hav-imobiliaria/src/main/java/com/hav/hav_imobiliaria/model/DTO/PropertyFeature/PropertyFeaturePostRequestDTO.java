package com.hav.hav_imobiliaria.model.DTO.PropertyFeature;

import com.hav.hav_imobiliaria.model.entity.Properties.PropertyFeature;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PropertyFeaturePostRequestDTO(
        @NotNull Boolean allowsPet,
        @NotNull @PositiveOrZero Integer bedRoom,
        @NotNull @PositiveOrZero Integer livingRoom,
        @NotNull @PositiveOrZero Integer suite,
        @NotNull @PositiveOrZero Integer bathRoom,
        @NotNull @PositiveOrZero Integer garageSpace,
        @NotNull Boolean isFurnished
) {
    public PropertyFeature convert() {
        return PropertyFeature.builder().allowsPet(allowsPet).bedRoom(bedRoom).livingRoom(livingRoom).
                bathRoom(bathRoom).garageSpace(garageSpace).suite(suite).isFurnished(isFurnished).build();
    }
}
