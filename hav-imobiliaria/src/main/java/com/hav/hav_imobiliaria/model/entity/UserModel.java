package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String user_name;
    private String email;
    private String user_password;
    private String cpf;
    private String phone_number;
    private String celphone;
    private Date birth_date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id", nullable = false)
    private UserAddressModel address;
}
