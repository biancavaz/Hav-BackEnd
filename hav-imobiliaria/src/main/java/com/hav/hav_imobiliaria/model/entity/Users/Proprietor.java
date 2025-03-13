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

    @Column(name = "number_Properties")
    private int numberProperties;

    @Column(nullable = false)
    private String purpose;

    @JsonBackReference
    @OneToMany(mappedBy = "proprietor")
    private List<Property> properties;

    
    public void incrementPropertyCount() {
        this.numberProperties++;
    }

    // Metodo para decrementar o número de imóveis (se necessário em uma exclusão)
    public void decrementPropertyCount() {
        if (this.numberProperties > 0) {
            this.numberProperties--;
        }
    }

}
