package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import lombok.Data;

@Data
public class ProprietorListGetResponseDTO {
    String cpf;
    String name;
    String email;
    Integer numberProperties;
    String goal;
}
