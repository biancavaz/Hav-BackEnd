package com.hav.hav_imobiliaria.model.dto;

import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.PropertyFeature;
import com.hav.hav_imobiliaria.model.entity.Taxes;

import java.time.LocalDateTime;

public record PropertyPostResquestDTO(
        String title,
        String propertyDescription,
        String propertyType,
        String purpose,
        String propertyStatus,
        Double area,
        Double price,
        Double promotionalPrice,
        Boolean highlight,
        String propertyCategory,
        LocalDateTime createdAt,
        Address address,
        Taxes taxes,
        PropertyFeature propertyFeatures
) {
}
