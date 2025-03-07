package com.hav.hav_imobiliaria.model.entity.Users;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@SuperBuilder
public class Customer extends User {

    @Column(name = "juristic_person")
    private Boolean juristicPerson;

    @Column(unique = true)
    private String cnpj;


}
