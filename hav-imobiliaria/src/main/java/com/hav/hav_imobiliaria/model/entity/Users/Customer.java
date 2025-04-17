package com.hav.hav_imobiliaria.model.entity.Users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customer")
@SuperBuilder
public class Customer extends User {

    @OneToOne
    @JoinColumn(name = "user_security_id", nullable = false, unique = true)
    private UserSecurity userSecurity;

    @JsonBackReference
    @Column
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedules> schedules;

}
