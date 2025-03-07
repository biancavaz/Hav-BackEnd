package com.hav.hav_imobiliaria.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, name = "property_number")
    private Integer propertyNumber;

    private String complement;

    @JsonBackReference
    @OneToMany(mappedBy = "address")
    private List<Property> property;

    @JsonBackReference
    @OneToMany(mappedBy = "address")
    private List<User> users;
}

