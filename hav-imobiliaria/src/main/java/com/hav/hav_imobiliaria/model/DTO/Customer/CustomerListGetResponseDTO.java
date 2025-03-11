package com.hav.hav_imobiliaria.model.DTO.Customer;

import lombok.Data;

@Data
public class CustomerListGetResponseDTO {
    Integer id;
    String cpf;
    String name;
    String email;
    String celphone;
    String status;
}
