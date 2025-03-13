package com.hav.hav_imobiliaria.model.entity.Properties;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Boolean allowsPet = false;

    @Column(name = "bed_room", nullable = false)
    private Integer bedRoom = 0;

    @Column(name = "living_room", nullable = false)
    private Integer livingRoom = 0;

    @Column(name = "bath_room", nullable = false)
    private Integer bathRoom = 0;

    @Column(nullable = false)
    private Integer suite = 0;

    @Column(name = "garage_space", nullable = false)
    private Integer garageSpace = 0;

    @Column(name = "is_furnished", nullable = false)
    private Boolean isFurnished = false;

    @JsonBackReference
    @OneToOne(mappedBy = "propertyFeatures")
    private Property property;
}
