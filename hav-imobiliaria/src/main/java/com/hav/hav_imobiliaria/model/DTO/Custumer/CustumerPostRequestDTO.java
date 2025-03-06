package com.hav.hav_imobiliaria.model.DTO.Custumer;

import com.hav.hav_imobiliaria.model.entity.Users.Custumer;
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

    public CustumerPostRequestDTO convertToDTO(Custumer custumer) {
        return new CustumerPostRequestDTO(
                custumer.getName(),
                custumer.getEmail(),
                custumer.getPassword(),
                custumer.getCelphone(),
                custumer.getBirthDate(),
                custumer.getCpf(),
                custumer.getJuristicPerson(),
                custumer.getCnpj(),
                custumer.getPhoneNumber());
    }
}
