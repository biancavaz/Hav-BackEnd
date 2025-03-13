package com.hav.hav_imobiliaria.model.DTO.PropertyFeature;

import com.hav.hav_imobiliaria.model.entity.Properties.PropertyFeature;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PropertyFeaturePostRequestDTO(
        @NotNull(message = "Permite Pet inválido")
        Boolean allowsPet,
        @NotNull(message = "Número de quartos inválido")
        @PositiveOrZero(message = "Número deve ser positivo ou zero")
        Integer bedRoom,
        @NotNull(message = "Número de salas inválido")
        @PositiveOrZero(message = "Número deve ser positivo ou zero")
        Integer livingRoom,
        @NotNull(message = "Número de suites inválido")
        @PositiveOrZero(message = "Número deve ser positivo ou zero")
        Integer suite,
        @NotNull(message = "Número de banheiros inválido")
        @PositiveOrZero(message = "Número deve ser positivo ou zero")
        Integer bathRoom,
        @NotNull(message = "Número de espaços de garagem inválido")
        @PositiveOrZero(message = "Número deve ser positivo ou zero")
        Integer garageSpace,
        @NotNull(message = "Imóvel mobiliado inválido") Boolean isFurnished) {

}
