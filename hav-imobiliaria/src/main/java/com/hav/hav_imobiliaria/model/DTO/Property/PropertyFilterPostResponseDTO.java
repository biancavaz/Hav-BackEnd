package com.hav.hav_imobiliaria.model.DTO.Property;

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


    public Double getMaxPric() {
        if(this.maxPric==null || this.maxPric==2000000.0 ){
            return 100000000.0;
        }
        return this.maxPric;
    }

    public Double getMinPric() {
        if(this.minPric==null) {
            return 100000.0;
        }
        return this.minPric;
    }
}
