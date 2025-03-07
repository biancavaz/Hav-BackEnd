package com.hav.hav_imobiliaria.model.entity.Users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hav.hav_imobiliaria.model.entity.Address;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(nullable = false)
    private Boolean archived;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;


}
