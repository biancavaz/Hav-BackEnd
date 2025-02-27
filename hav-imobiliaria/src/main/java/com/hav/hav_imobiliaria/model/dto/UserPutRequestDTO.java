package com.hav.hav_imobiliaria.model.dto;

import com.hav.hav_imobiliaria.model.entity.User;

public record UserPutRequestDTO(
        String nome,
        String email,
        String cpf,
        String celphone) {

    public User convert(){
        return User.builder()
                .name(nome)
                .email(email)
                .cpf(cpf)
                .celphone(celphone)
                .build();
    }
}
