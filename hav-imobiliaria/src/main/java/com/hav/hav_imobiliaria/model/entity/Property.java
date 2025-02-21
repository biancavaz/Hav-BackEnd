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
@Table(name = "property")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String property_description;
    private String property_type;
    private String purpose;
    private String property_status;
    private Double area;
    private Double price;
    private Double promotional_price;
    private Boolean highlight;
    private String property_category;

    @CreationTimestamp
    private LocalDateTime created_at;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToOne
    @JoinColumn(name = "taxes_id", referencedColumnName = "id", nullable = false)
    private Taxes taxes;

    @OneToOne
    @JoinColumn(name = "property_feature_id", referencedColumnName = "id", nullable = false)
    private PropertyFeature propertyFeatures;

}
