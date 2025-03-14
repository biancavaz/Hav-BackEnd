package com.hav.hav_imobiliaria.model.entity.Users;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "proprietor")
public class Proprietor extends User {

    @Column(unique = true)
    private String cnpj;

    @JsonBackReference
    @OneToMany(mappedBy = "proprietor")
    private List<Property> properties;


    public Integer numberOfProperty() {
        return properties.size();
    }

    public String getPurpose() {
        boolean temVenda = false;
        boolean temLocacao = false;

        // Percorrendo todos os imóveis para verificar os propósitos
        for (Property property : properties) {
            if (property.getPurpose().equalsIgnoreCase("venda")) {
                temVenda = true;
            } else if (property.getPurpose().equalsIgnoreCase("locacao")) {
                temLocacao = true;
            }
        }

        // Definindo o propósito com base nos imóveis encontrados
        if (temVenda && temLocacao) {
            return "Misto";
        } else if (temVenda) {
            return "Venda";
        } else if (temLocacao) {
            return "Locação";
        } else {
            return "Nenhum";
        }
    }


}
