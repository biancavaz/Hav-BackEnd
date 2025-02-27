package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "user") //desmuder para users
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED) // herança
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_users", nullable = false, unique = true)
        @NotNull
        private Integer id;

        @NotNull
        private String name;
        @NotNull
        private String email;
        @NotNull
        private String password;
        @NotNull
        private String cpf;
        @NotNull
        private String celphone;
        private String phoneNumber;
        @NotNull
        private Date birthDate;


//        @OneToMany(cascade = CascadeType.ALL)
//        @JoinColumn(name = "id_address", nullable = false)
//        private Address address;

}
