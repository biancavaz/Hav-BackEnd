package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import lombok.Data;

@Data
public class ProprietorFilterPostResponseDTO {
    String cpf;
    String cnpj;
    String name;
    String email;
    Integer numberOfProperty;
    String goal;
    boolean archived;
}
