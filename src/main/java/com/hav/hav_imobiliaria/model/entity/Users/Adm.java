package com.hav.hav_imobiliaria.model.entity.Users;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "adm")
public class Adm extends User {

    @OneToOne
    @JoinColumn(name = "user_security_id", nullable = false, unique = true)
    private UserSecurity userSecurity;

}
