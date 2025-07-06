package com.hav.hav_imobiliaria.model.DTO.PropertyFeature;

import com.hav.hav_imobiliaria.model.entity.Properties.PropertyFeature;
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

    public PropertyFeatureCardGetResponseDTO(PropertyFeature propertyFeatures) {
        this.bathRoom = propertyFeatures.getBathRoom();
        this.bedRoom = propertyFeatures.getBedRoom();
        this.livingRoom = propertyFeatures.getLivingRoom();
        this.garageSpace = propertyFeatures.getGarageSpace();
        this.suite = propertyFeatures.getSuite();
    }
}
