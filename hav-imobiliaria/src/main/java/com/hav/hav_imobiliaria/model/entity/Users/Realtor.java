package com.hav.hav_imobiliaria.model.entity.Users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@Table(name = "realtor")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Realtor extends User {

    @Column(nullable = false, unique = true)
    private String creci;

}
