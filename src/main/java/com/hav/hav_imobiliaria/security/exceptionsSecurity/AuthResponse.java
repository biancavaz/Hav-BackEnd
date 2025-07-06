package com.hav.hav_imobiliaria.security.exceptionsSecurity;

import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private boolean status;
    private String jwt;
    private UserSecurity user;
    private String message;
}
