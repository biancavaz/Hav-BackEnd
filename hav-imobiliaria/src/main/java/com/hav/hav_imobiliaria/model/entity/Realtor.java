package com.hav.hav_imobiliaria.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "realtor")
@AllArgsConstructor
@NoArgsConstructor
public class Realtor extends User {

    @Column(nullable = false, unique = true)
    private String creci;

    @JsonBackReference
    @ManyToMany(mappedBy = "realtors")
    private List<Property> properties;
}
