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
@Table(name = "customer_owner")
@SuperBuilder
public class Custumer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Integer id;

    @Column(name = "juristic_person")
    private Boolean juristicPerson;

    @Column(unique = true)
    private String cnpj;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "id_users")
    private User user;

}
