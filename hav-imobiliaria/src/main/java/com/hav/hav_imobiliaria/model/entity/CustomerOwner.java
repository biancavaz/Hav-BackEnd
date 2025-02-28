package com.hav.hav_imobiliaria.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_owner")
@SuperBuilder
//@DiscriminatorValue("custumerOwner")
public class CustomerOwner extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer_owner")
    private Integer id;

    @Column(name = "juristic_person", nullable = false)
    private Boolean juristicPerson;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @JsonBackReference
    @OneToMany(mappedBy = "owner")
    private List<Property> properties;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

}
