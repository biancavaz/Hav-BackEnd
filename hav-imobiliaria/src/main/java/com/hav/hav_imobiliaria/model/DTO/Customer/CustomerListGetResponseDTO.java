package com.hav.hav_imobiliaria.model.DTO.Customer;

import lombok.Data;

@Data
public class CustomerListGetResponseDTO {
    String cpf;
    String name;
    String email;
    Integer numberProperties=1;
    String goal="1";

}
