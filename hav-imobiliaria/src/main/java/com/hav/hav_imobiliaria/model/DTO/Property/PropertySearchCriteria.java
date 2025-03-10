package com.hav.hav_imobiliaria.model.DTO.Property;

import lombok.Data;

@Data
public class PropertySearchCriteria {
    private String propertyCode;
    private String proprietorName;
    private String propertyType;
    private String purpose;
    private String propertyStatus;
    private Double minPrice;
    private Double maxPrice;
}
