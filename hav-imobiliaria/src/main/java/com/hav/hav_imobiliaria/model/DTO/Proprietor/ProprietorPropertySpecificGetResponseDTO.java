package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProprietorPropertySpecificGetResponseDTO {
    String image;

    String name;
    String email;
    String phoneNumber;

}
