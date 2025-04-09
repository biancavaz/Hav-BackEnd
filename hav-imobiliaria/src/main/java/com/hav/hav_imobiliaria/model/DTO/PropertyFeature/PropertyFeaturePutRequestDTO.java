package com.hav.hav_imobiliaria.model.DTO.PropertyFeature;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isFurnished") // Garante que o nome não seja alterado
    boolean isFurnished;


}
