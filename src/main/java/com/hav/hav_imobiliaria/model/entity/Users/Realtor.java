package com.hav.hav_imobiliaria.model.entity.Users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "realtor")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Realtor extends User {

    @OneToOne
    @JoinColumn(name = "user_security_id", nullable = false, unique = true)
    private UserSecurity userSecurity;

    @Column(nullable = false, unique = true)
    private String creci;


}
