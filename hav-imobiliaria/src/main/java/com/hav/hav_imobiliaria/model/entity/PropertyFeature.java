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

    @Column(name = "allows_pet", nullable = false)
    private Boolean allowsPet;

    @Column(name = "bed_room", nullable = false)
    private Integer bedRoom;

    @Column(name = "living_room", nullable = false)
    private Integer livingRoom;

    @Column(name = "bath_room", nullable = false)
    private Integer bathRoom;

    @Column(nullable = false)
    private Integer suite;

    @Column(name = "garage_space", nullable = false)
    private Integer garageSpace;

    @Column(name = "is_furnished", nullable = false)
    private Boolean isFurnished;

    @OneToOne(mappedBy = "propertyFeatures")
    private Property property;
}
