package com.hav.hav_imobiliaria.model.DTO.CustumerOwner;

import com.hav.hav_imobiliaria.model.entity.CustumerOwner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustumerOwnerPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @Pattern(regexp = "\\d{11}") String cpf,
        Boolean juristicPerson,
        @Pattern(regexp = "\\\\d{2}\\\\.\\\\d{3}\\\\.\\\\d{3}/\\\\d{4}-\\\\d{2}") String cnpj) {

    public CustumerOwner convert(){
        return CustumerOwner.builder()
                .name(name)
                .email(email)
                .password(password)
                .cpf(cpf)
                .juristicPerson(juristicPerson)
                .cnpj(cnpj)
                .build();
    }
}
