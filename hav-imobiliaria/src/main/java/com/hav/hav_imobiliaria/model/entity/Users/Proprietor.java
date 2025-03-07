package com.hav.hav_imobiliaria.model.entity.Users;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "proprietor")
public class Proprietor extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proprietor", nullable = false, unique = true)
    private Integer id;

    @Column(name = "juristic_person")
    private Boolean juristicPerson;

    @Column(unique = true)
    private String cnpj;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

}
