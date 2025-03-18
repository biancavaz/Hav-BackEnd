package com.hav.hav_imobiliaria.model.DTO.Additionals;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AdditionalsPutRequestDto {
    private Integer id;
    private String name;
}
