package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeatureFilterPostResponseDto;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePutRequestDTO;
import lombok.Data;

@Data
public class PropertyFilterPostResponseDTO {
    String propertyCode;
    Double minPric;
    Double maxPric;
    String propertyType;
    String propertyCategory;
    String propertyStatus;
    boolean archived;
    PropertyFeatureFilterPostResponseDto propertyFeatures;


    public PropertyFeatureFilterPostResponseDto getPropertyFeatures() {
        return propertyFeatures;
    }

    public Double getMaxPric() {
        if(this.maxPric==null){
            return 100000000.0;
        }
        return this.maxPric;
    }

    public Double getMinPric() {
        if(this.minPric==null) {
            return 0.0;
        }
        return this.minPric;
    }
}
