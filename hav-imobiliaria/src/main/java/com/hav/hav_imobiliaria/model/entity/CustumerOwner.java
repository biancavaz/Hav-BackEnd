package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_owner")
//@Builder
public class CustumerOwner extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer_owner")
    private Integer id;

    @Column(name = "juristic_person", nullable = false)
    private Boolean juristicPerson;

    @Column(nullable = false, unique = true)
    private String cnpj;

}
