package com.hav.hav_imobiliaria.model.DTO.Property;

import lombok.Data;

@Data
public class PropertyFilterPostResponseDTO {
    String propertyCode;
    String price;
    String propertyType;
    String propertyCategory;
    String propertyStatus;
}
