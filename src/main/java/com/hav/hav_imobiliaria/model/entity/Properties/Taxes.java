package com.hav.hav_imobiliaria.model.entity.Properties;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private Double condominiumFee = 0.0;

    private Double iptu = 0.0;

    @JsonBackReference
    @OneToOne(mappedBy = "taxes")
    private Property property;
}
