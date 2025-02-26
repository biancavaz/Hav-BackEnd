package com.hav.hav_imobiliaria.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String propertyDescription;
    @Column(nullable = false)
    private String propertyType;
    @Column(nullable = false)
    private String purpose;
    @Column(nullable = false)
    private String propertyStatus;
    @Column(nullable = false)
    private Double area;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Double promotionalPrice;
    @Column(nullable = false)
    private Boolean highlight;
    @Column(nullable = false)
    private String propertyCategory;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "id_property_address", nullable = false)
    private Address address;

    @OneToOne
    @JoinColumn(name = "id_taxes", nullable = false)
    private Taxes taxes;

    @OneToOne
    @JoinColumn(name = "id_property_feature", nullable = false)
    private PropertyFeature propertyFeatures;

    @OneToMany(mappedBy = "property")
    private List<Additionals> additionals;

}
