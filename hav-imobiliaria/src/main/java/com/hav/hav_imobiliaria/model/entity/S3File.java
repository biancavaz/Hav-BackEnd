package com.hav.hav_imobiliaria.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "S3File")
public class S3File {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String originalFileName;
    private String s3FilePath;
    private String fileUrl;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id_property", nullable = false)
    private Property property;
}
