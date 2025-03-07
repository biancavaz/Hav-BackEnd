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


    @Column(name = "juristic_person")
    private Boolean juristicPerson;

    @Column(unique = true)
    private String cnpj;


}
