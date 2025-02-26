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
public class PropertyFeatureModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Boolean allows_pet;
    @Column(nullable = false)
    private Integer bed_room;
    @Column(nullable = false)
    private Integer living_room;
    @Column(nullable = false)
    private Integer bath_room;
    @Column(nullable = false)
    private Integer suite;
    @Column(nullable = false)
    private Integer garage_space;
    @Column(nullable = false)
    private Boolean is_furnished;

    @OneToOne(mappedBy = "propertyFeatures")
    private PropertyModel property;
}
