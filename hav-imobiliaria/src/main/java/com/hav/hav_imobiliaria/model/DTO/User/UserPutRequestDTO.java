package com.hav.hav_imobiliaria.model.DTO.User;

import com.hav.hav_imobiliaria.model.entity.User.User;

public record UserPutRequestDTO(
        String nome,
        String email,
        String cpf,
        String celphone) {

    public User convert() {
        return User.builder()
                .name(nome)
                .email(email)
                .cpf(cpf)
                .celphone(celphone)
                .build();
    }
}