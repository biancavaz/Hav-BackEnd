package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
=======
import jakarta.validation.constraints.NotNull;
>>>>>>> branch_da_bia
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
<<<<<<< HEAD
@Table(name = "tb_realtor")
@Builder
@AllArgsConstructor
@NoArgsConstructor
=======
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "realtor")
@Builder
>>>>>>> branch_da_bia
public class Realtor extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    private Integer id;
=======
    @Column(name = "id_realtor", unique = true, nullable = false)
    private int id;

    @NotNull
>>>>>>> branch_da_bia
    private String creci;


}
