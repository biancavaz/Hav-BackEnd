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
    @Column(nullable = false, unique = true)
    @NotNull
    private Integer id;

    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "id_users", nullable = false)
    private User user;

}
