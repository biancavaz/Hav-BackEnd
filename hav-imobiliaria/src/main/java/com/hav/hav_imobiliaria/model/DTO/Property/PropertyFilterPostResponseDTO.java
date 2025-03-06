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
}
