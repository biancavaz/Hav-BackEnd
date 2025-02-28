package com.hav.hav_imobiliaria.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("adm")
public class Adm extends User {
    //perguntar ao professor sobre isso
    //faz sentido, até por conta dos endpoints
}
