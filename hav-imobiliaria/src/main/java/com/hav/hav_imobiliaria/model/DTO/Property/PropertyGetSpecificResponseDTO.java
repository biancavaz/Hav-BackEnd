package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsPutRequestDto;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeatureGetResposeDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeatureSpecifiGetRespondeDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPutRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PropertyGetSpecificResponseDTO {

    @NotBlank
    String propertyCode;
    @NotBlank
    String propertyType;
    @NotBlank
    String propertyStatus;
    @NotBlank
    String purpose;
    @NotNull
    List<RealtorGetResponseDTO> realtorList;
    @NotBlank
    String propertyDescription;
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
    String floors;
    @NotNull
    TaxesPutRequestDTO taxes;
    @NotNull
    Boolean isFurnished;
    @NotNull
    AddressGetResponseDTO address;
    @NotNull
    PropertyFeatureSpecifiGetRespondeDTO propertyFeature; //nao esta funcionando
    @NotNull
    AdditionalsGetResponseDTO additional;

}
