package com.hav.hav_imobiliaria.model.DTO.PropertyFeature;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyFeatureGetResposeDTO {

    Integer suite;
    Boolean allowsPet = false;
    Integer bedRoom;
    Integer livingRoom;
    Integer bathRoom;
    Integer garageSpace;
    Boolean isFurnished = false;
}
