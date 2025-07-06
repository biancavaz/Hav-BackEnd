package com.hav.hav_imobiliaria.model.DTO.Login;


import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
    private String token;
    private String newPassword;
}
