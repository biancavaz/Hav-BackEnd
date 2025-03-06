package com.hav.hav_imobiliaria.model.DTO.User;

public record UserResponseDTO(
        Integer id,
        String nome,
        String email,
        String cpf,
        String celphone,
        String birthDate) {
}