package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_address")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddressModel {

    @OneToOne(mappedBy = "address")
    @NotNull
    private User user;

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
    @Column(nullable = false)
    private String property_number;
    private String complement;

    @OneToOne(mappedBy = "address")
    private UserModel user;
}
