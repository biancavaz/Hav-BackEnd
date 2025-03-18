package com.hav.hav_imobiliaria.model.DTO.PropertyFeature;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PropertyFeaturePutRequestDTO {
    boolean allowsPet;
    Integer bedRoom;
    Integer livingRoom;
    Integer suite;
    Integer bathRoom;
    Integer garageSpace;
    boolean isFurnished;
}
