package com.hav.hav_imobiliaria.security.modelSecurity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String full_name;
    private String email;
    private String profile_picture;
    private String password;

}
