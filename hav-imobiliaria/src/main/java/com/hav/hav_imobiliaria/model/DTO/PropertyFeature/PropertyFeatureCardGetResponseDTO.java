package com.hav.hav_imobiliaria.model.DTO.PropertyFeature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyFeatureCardGetResponseDTO {
    Integer bathRoom;
    Integer bedRoom;
    Integer livingRoom;
    Integer garageSpace;
    Integer suite;

}
