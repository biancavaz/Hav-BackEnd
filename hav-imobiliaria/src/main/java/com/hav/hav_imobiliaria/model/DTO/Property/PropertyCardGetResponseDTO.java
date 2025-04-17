package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeatureCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.Properties.PropertyFeature;
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
    @NotNull
    Double price;
    @NotNull
    String purpose;
    @NotNull
    String propertyStatus;
    Double promotionalPrice;
    Integer id;
    String propertyType;
    Double area;
    Integer mainImageId;

    public PropertyCardGetResponseDTO(PropertyFeature propertyFeatures, Address address, Double price, String purpose, String propertyStatus, Double promotionalPrice, Integer id, String propertyType, Double area) {
        this.propertyFeatures = new PropertyFeatureCardGetResponseDTO(propertyFeatures);
        this.address = new AddressCardGetResponseDTO(address);
        this.price = price;
        this.purpose = purpose;
        this.propertyStatus = propertyStatus;
        this.promotionalPrice = promotionalPrice;
        this.id = id;
        this.propertyType = propertyType;
        this.area = area;
    }
}
