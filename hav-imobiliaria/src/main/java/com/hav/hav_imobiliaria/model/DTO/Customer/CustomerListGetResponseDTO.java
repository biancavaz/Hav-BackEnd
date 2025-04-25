package com.hav.hav_imobiliaria.model.DTO.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerListGetResponseDTO {
    Integer id;
    String cpf;
    String name;
    String email;
    String cellphone;
    Boolean status;
}
