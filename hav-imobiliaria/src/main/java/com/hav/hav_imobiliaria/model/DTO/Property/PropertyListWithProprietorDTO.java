package com.hav.hav_imobiliaria.model.DTO.Property;

import lombok.Data;

@Data
public class PropertyListWithProprietorDTO {
    private String propertyCode;
    private String proprietorName; // Substitui o campo price
    private String propertyType;
    private String propertyCategory;
    private String propertyStatus;
}
