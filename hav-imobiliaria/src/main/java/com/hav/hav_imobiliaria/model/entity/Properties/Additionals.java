package com.hav.hav_imobiliaria.model.entity.Properties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "additionals")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Additionals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "additionals")
    private List<Property> properties;
}
