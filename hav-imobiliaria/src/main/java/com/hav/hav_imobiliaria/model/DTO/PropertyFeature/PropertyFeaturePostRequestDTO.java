package com.hav.hav_imobiliaria.model.DTO.PropertyFeature;

import com.hav.hav_imobiliaria.model.entity.Properties.PropertyFeature;

public record PropertyFeaturePostRequestDTO(
        Boolean allowsPet,
        Integer bedRoom,
        Integer livingRoom,
        Integer bathRoom,
        Integer garageSpace,
        Boolean isFurnished
) {
    public PropertyFeature convert() {
        return PropertyFeature.builder().allowsPet(allowsPet).bedRoom(bedRoom).livingRoom(livingRoom).
                bathRoom(bathRoom).garageSpace(garageSpace).isFurnished(isFurnished).build();
    }
}
