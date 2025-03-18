package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsPutRequestDto;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class PropertyPutRequestDTO{
        @NotBlank String title;
        @NotBlank String propertyDescription;
        @NotBlank String propertyType;
        @NotBlank String purpose;
        @NotBlank String propertyStatus;
        @Positive @NotNull Double area;
        @Positive @NotNull Double price;
        @PositiveOrZero Double promotionalPrice;
        @NotNull Boolean highlight;
        @NotBlank String propertyCategory;
        @NotNull String floors;
        @NotNull
        TaxesPutRequestDTO taxes;
        @NotNull
        PropertyFeaturePutRequestDTO propertyFeatures;
        @NotNull AddressPutRequestDTO address;
        @NotNull List<RealtorPutRequestDTO> realtors;
        @NotNull
        ProprietorPutRequestDTO proprietor;

//        List<AdditionalsPutRequestDto> additionals;


}
