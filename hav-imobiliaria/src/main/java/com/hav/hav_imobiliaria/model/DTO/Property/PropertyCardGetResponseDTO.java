package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeatureCardGetResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyCardGetResponseDTO {

    @NotNull
    PropertyFeatureCardGetResponseDTO propertyFeatures;
    @NotNull
    AddressCardGetResponseDTO address;
    Integer id;

    @NotNull
    Double price;
    @NotNull
    String purpose;
    @NotNull
    String propertyStatus;
    Double promotionalPrice;
    Integer id;


}
