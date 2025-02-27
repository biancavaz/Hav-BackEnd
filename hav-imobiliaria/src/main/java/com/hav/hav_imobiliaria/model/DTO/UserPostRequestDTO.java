package com.hav.hav_imobiliaria.model.DTO;

import com.hav.hav_imobiliaria.model.entity.User;

public record UserPostRequestDTO(
        String name,
        String email,
        String password) {

    public User convert(){
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
