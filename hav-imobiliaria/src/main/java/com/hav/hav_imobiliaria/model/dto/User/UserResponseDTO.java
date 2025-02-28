package com.hav.hav_imobiliaria.model.dto.User;

public record UserResponseDTO(
        Integer id,
        String nome,
        String email,
        String cpf,
        String celphone,
        String birthDate) {


}
