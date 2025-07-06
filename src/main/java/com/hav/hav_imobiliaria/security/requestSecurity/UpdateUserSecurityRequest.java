package com.hav.hav_imobiliaria.security.requestSecurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserSecurityRequest {

    private String full_name;
    private String profile_picture;
}
