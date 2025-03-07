package com.hav.hav_imobiliaria.model.entity.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@Table(name = "realtor")
@SuperBuilder
@NoArgsConstructor
public class Realtor extends User {

    @Column(nullable = false, unique = true)
    private String creci;

    @JsonBackReference
    @ManyToMany(mappedBy = "realtors")
    private List<Property> properties;

    public Realtor(UserBuilder<?, ?> user, String creci) {
        super(user); // Passa o User para o construtor da superclasse
        this.creci = creci;
    }
}
