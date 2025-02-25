package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "property_feature")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean allows_pet;
    private Integer bed_room;
    private Integer living_room;
    private Integer bath_room;
    private Integer suite;
    private Integer garage_space;
    private Boolean is_furnished;

    @OneToOne(mappedBy = "propertyFeatures")
    private Property property;
}
