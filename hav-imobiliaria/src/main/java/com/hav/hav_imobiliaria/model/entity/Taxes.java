package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "taxes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Taxes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double condominium_fee;
    private Double iptu;

    @OneToOne(mappedBy = "taxes")
    private Property property;
}
