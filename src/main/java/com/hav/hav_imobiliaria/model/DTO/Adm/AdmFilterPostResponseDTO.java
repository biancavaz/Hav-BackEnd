package com.hav.hav_imobiliaria.model.DTO.Adm;

import lombok.Data;

@Data
public class AdmFilterPostResponseDTO {
    Integer id;
    String cpf;
    String name;
    String email;
    String celphone;
    String phoneNumber;
    boolean archived;

}
