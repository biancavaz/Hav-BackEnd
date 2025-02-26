package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
=======
import jakarta.validation.constraints.NotNull;
>>>>>>> branch_da_bia
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
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
    private String cep;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
    private String number;
    private String complement;

=======

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
@Builder
public class Address {

    @OneToOne(mappedBy = "address")
    @NotNull
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", unique = true, nullable = false)
    @NotNull
    private int id;

    @NotNull
    private String city;
    @NotNull
    private String cep;
    @NotNull
    private String state;
    @NotNull
    private String street;
    @NotNull
    private String neighborhood;
    @NotNull
    private String propertyNumber;
    @NotNull
    private String complement;
>>>>>>> branch_da_bia
}
