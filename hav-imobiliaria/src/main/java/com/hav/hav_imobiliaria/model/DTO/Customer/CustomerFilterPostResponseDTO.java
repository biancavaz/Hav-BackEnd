package com.hav.hav_imobiliaria.model.DTO.Customer;

import lombok.Data;

@Data
public class CustomerFilterPostResponseDTO {
    String cpf;
    String name;
    String email;
    String celphone;
    String status;
    boolean archived;

}
