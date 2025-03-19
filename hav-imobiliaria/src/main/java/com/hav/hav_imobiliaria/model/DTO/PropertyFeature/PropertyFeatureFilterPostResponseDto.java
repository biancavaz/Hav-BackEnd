package com.hav.hav_imobiliaria.model.DTO.PropertyFeature;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class PropertyFeatureFilterPostResponseDto {
    Integer bedRoom;
    Integer bathRoom;
    Integer garageSpace;
    Integer suite;
    Boolean isFunished;
    Boolean allowsPet;
}
