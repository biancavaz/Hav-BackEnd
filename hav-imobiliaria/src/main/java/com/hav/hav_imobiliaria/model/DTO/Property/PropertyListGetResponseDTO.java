package com.hav.hav_imobiliaria.model.DTO.Property;

import lombok.Data;

@Data
public class PropertyListGetResponseDTO {
    Integer id;
    String propertyCode;
    Double price;
    String propertyType;
    String propertyCategory;
    String propertyStatus;
}
