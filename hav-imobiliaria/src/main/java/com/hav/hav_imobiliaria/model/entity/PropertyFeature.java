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
    @Column(nullable = false)
    private Boolean allowsPet;
    @Column(nullable = false)
    private Integer bedRoom;
    @Column(nullable = false)
    private Integer livingRoom;
    @Column(nullable = false)
    private Integer bathRoom;
    @Column(nullable = false)
    private Integer suite;
    @Column(nullable = false)
    private Integer garageSpace;
    @Column(nullable = false)
    private Boolean isFurnished;

//    @OneToOne(mappedBy = "propertyFeatures")
//    private Property property;
}
