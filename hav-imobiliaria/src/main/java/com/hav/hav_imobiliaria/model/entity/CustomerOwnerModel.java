package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "customer_owner")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOwnerModel extends UserModel {

    @Column(nullable = false, unique = true)
    private String cnpj;
}

