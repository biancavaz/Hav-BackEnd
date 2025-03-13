package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import lombok.Data;

@Data
public class ProprietorListGetResponseDTO {
    Integer id;
    String cpf;
    String name;
    String email;
    String goal;
    Integer numberOfProperty;
    String definirPropósito;
}
