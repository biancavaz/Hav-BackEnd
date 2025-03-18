package com.hav.hav_imobiliaria.model.DTO.Taxes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class TaxesPutRequestDTO {
    Double condominiumFee;
    Double iptu;
}
