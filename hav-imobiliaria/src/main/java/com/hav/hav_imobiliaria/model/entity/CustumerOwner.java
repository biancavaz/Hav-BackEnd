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
@Builder
public class CustumerOwner extends User {

    @Column(name = "juristic_person", nullable = false)
    private Boolean juristicPerson;

    @Column(nullable = false, unique = true)
    private String cnpj;

}
