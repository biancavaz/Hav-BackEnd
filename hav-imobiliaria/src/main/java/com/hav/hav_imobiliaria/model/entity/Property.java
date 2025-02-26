package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
<<<<<<< HEAD
@Table(name = "property")
=======
@Table(name = "properties")
>>>>>>> branch_da_bia
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String property_type;
    private String purpose;
    private String status;
    private Double area;
    private Double price;
    private Double promotional_price;
    private Boolean is_featured;
    private String house_type;
    @CreationTimestamp
    private LocalDateTime created_at;

}
