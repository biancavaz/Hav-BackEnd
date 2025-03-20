package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsPutRequestDto;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class PropertyGetSpecificRequestDTO {
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
    String title;
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

    List<AdditionalsPutRequestDto> additionals;


}
