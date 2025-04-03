package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record PropertyPostRequestDTO(
        String propertyCode,
        @NotBlank(message = "Título não pode estar em branco")
        String title,
        @NotBlank(message = "Descrição não pode estar em branco")
        String propertyDescription,
        @NotBlank(message = "Tipo de propriedade não pode estar em branco")
        String propertyType,
        @NotBlank(message = "Finalidade não pode estar em branco")
        String purpose,
        @NotBlank(message = "Status não pode estar em branco")
        String propertyStatus,
        @Positive(message = "Área deve ser positiva")
        @NotNull(message = "Área inválida")
        Double area,
        @Positive(message = "Andares deve ser positivo")
        @NotNull(message = "Número de andares inválido")
        Integer floors,
        @Positive(message = "Preço deve ser positivo")
        @NotNull(message = "Preço inválido")
        Double price,
        @PositiveOrZero(message = "Preço deve ser positivo ou zero")
        Double promotionalPrice,
        @NotNull(message = "Destaque inválido")
        Boolean highlight,
//        @NotBlank(message = "Categoria não pode estar em branco")
//        String propertyCategory,
        @Valid
        @NotNull(message = "Endereço inválido")
        AddressPostRequestDTO address,
        @Valid
        @NotNull(message = "Taxas inválidas")
        TaxesPostRequestDTO taxes,
        @Valid
        @NotNull(message = "Características inválidas")
        PropertyFeaturePostRequestDTO propertyFeatures,


        List<Integer> additionals,
        @NotNull(message = "Ids corretores inválidos")
        List<Integer> realtors,
        @Positive(message = "Id deve ser positivo")
        @NotNull(message = "Proprietário inválido")
        Integer proprietor
) {
    public Property convert() {
        return Property.builder().title(title).
                propertyDescription(propertyDescription).
                propertyType(propertyType).propertyStatus(propertyStatus).area(area).price(price).
                promotionalPrice(promotionalPrice).purpose(purpose).highlight(highlight).
//                propertyCategory(propertyCategory).
                floors(floors).
                address(address.convert()).taxes(taxes.convert()).
            propertyFeatures(propertyFeatures.convert())
                .build();
    }
}
