package com.hav.hav_imobiliaria.model.DTO.Additionals;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdditionalsGetRequestDTO {
    Integer id;

    String name;
}
