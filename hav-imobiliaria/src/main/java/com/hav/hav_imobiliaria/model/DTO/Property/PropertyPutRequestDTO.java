package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPutRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class PropertyPutRequestDTO {
    @NotBlank
    String title;
    @NotBlank
    String propertyDescription;
    @NotBlank
    String propertyType;
    @NotBlank
    String purpose;
    @NotBlank
    String propertyStatus;
    @Positive
    @NotNull
    Double area;
    @Positive
    @NotNull
    Double price;
    @PositiveOrZero
    Double promotionalPrice;
    @NotNull
    Boolean highlight;
    @NotBlank
    String propertyCategory;
    @NotNull
    Integer floors;
    @NotNull
    TaxesPutRequestDTO taxes;
    @NotNull
    PropertyFeaturePutRequestDTO propertyFeatures;
    List<Integer> realtors;
    @NotNull
    Integer proprietor;
    @NotNull
    List<Integer> additionals;
}
