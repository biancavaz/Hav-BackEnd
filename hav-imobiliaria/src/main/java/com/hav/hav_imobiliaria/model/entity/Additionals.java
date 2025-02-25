package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "additionals")
@NoArgsConstructor
@AllArgsConstructor
public class Additionals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
}
