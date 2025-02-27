package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_realtor", unique = true, nullable = false)
    private Integer id;

    @NotNull
    private String creci;


}
