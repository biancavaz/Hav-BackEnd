package com.hav.hav_imobiliaria.model.DTO.Customer;

import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import jakarta.validation.constraints.NotBlank;

public record CustumerPutRequestDTO(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String celphone) {

    public Customer convert(){
        return Customer.builder()

                .email(email)
                .password(password)
                .celphone(celphone)
                .build();
    }
}
