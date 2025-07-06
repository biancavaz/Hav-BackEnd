package com.hav.hav_imobiliaria.model.DTO.Customer;

import lombok.Data;

@Data
public class CustomerFilterPostResponseDTO {
    String cpf;
    String name;
    String email;
    String cellphone;
    String status;
    boolean archived;

}
