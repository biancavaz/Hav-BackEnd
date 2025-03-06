package com.hav.hav_imobiliaria.model.entity.Users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
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



    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn(name = "id_realtor")
    private Realtor realtor;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn(name = "id_custumer")
    private Custumer custumer;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn(name = "id_editor")
    private Editor editor;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn(name = "id_adm")
    private Adm adm;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn(name = "id_Proprietor")
    private Proprietor proprietor;

//    @JsonBackReference
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id_address")
//    private Address address;
    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;


}
