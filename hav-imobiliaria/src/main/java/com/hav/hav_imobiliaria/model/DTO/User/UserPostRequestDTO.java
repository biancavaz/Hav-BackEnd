package com.hav.hav_imobiliaria.model.DTO.User;

import com.hav.hav_imobiliaria.model.entity.User;
import jakarta.validation.constraints.NotNull;

public record UserPostRequestDTO(
        @NotNull String name,
        @NotNull String email,
        @NotNull String password) {

    public User convert(){
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
