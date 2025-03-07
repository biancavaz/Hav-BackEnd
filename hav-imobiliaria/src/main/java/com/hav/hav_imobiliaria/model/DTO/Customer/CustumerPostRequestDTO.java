package com.hav.hav_imobiliaria.model.DTO.Customer;

import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public record CustumerPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull String celphone,
        @NotNull Date birthDate,
        @Pattern(regexp = "\\d{11}") String cpf,
        Boolean juristicPerson,
        @Pattern(regexp = "\\\\d{2}\\\\.\\\\d{3}\\\\.\\\\d{3}/\\\\d{4}-\\\\d{2}") String cnpj,
        String phoneNumber) {

    public CustumerPostRequestDTO convertToDTO(Customer customer) {
        return new CustumerPostRequestDTO(
                customer.getName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getCelphone(),
                customer.getBirthDate(),
                customer.getCpf(),
                customer.getJuristicPerson(),
                customer.getCnpj(),
                customer.getPhoneNumber());
    }
}
