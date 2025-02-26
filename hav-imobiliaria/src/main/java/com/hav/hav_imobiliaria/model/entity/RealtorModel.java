package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "realtor")
@NoArgsConstructor
@AllArgsConstructor
public class RealtorModel extends UserModel {

    @Column(nullable = false, unique = true)
    private String creci;
}
