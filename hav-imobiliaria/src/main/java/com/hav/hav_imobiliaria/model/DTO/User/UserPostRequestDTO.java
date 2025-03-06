package com.hav.hav_imobiliaria.model.DTO.User;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

public record UserPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String cpf,
        @NotBlank String celphone,
        @NotBlank String phoneNumber,
        @NotNull Date birthDate,
        @NotNull AddressPostRequestDTO addressDTO
) {

    public User convert() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .cpf(cpf)
                .phoneNumber(phoneNumber)
                .celphone(celphone)
                .birthDate(birthDate)
                .address(addressDTO.convert())
                .build();
    }
}
