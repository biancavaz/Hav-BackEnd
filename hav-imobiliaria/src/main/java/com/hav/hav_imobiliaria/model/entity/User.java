package com.hav.hav_imobiliaria.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder // usar essa notação quando tiver herança (pra n dar problema na dto)
@Inheritance(strategy = InheritanceType.JOINED) // herança
//@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String celphone;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    private Realtor realtor;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    private CustomerOwner owner;
}
