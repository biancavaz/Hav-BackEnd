package com.hav.hav_imobiliaria.model.entity.Users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

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
    @Column(name = "id_users", nullable = false, unique = true)
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

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "id_realtor", nullable = false)
    private Realtor realtor;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "id_custumer", nullable = false)
    private Custumer custumer;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "id_editor", nullable = false)
    private Editor editor;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "id_adm", nullable = false)
    private Adm adm;

    @JsonBackReference
    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "id_adm", nullable = false)
    private Proprietor proprietor;

    //ainda n sei como fazer este
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "id_address", nullable = false)
//    private Address address;

}
