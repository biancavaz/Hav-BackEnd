package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "realtor")
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Realtor extends User {

    @Column(nullable = false, unique = true)
    private String creci;
}
