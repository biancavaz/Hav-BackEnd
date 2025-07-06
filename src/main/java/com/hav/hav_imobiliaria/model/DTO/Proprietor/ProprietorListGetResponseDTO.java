package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import lombok.Data;

@Data
public class ProprietorListGetResponseDTO {
    Integer id;
    String document; //cpf e cnpj
    String name;
    String email;
    Integer numberOfProperty;
    String purpose;



    public void setDocument(String document) {
        this.document = document;
    }
}
