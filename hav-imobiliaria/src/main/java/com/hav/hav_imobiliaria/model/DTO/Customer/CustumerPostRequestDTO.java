package com.hav.hav_imobiliaria.model.DTO.Customer;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public record CustumerPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotNull String celphone,
        @NotNull Date birthDate,
        @Pattern(regexp = "\\d{11}") String cpf,
        String phoneNumber,
        @NotNull Boolean archived,
        @NotNull @Valid AddressPostRequestDTO address
) {

    public CustumerPostRequestDTO convertToDTO(Customer customer) {
        return new CustumerPostRequestDTO(
                customer.getName(),
                customer.getEmail(),
                customer.getCelphone(),
                customer.getBirthDate(),
                customer.getCpf(),
                customer.getPhoneNumber(),
                customer.getArchived(),
                address);
    }
}
