package com.hav.hav_imobiliaria.model.entity.Properties;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "image_property")
@NoArgsConstructor
@AllArgsConstructor
public class ImageProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String s3Key;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_property", nullable = false)
    private Property property;

    public ImageProperty(String s3Key, Property property) {
        this.s3Key = s3Key;
        this.property = property;
    }
}
