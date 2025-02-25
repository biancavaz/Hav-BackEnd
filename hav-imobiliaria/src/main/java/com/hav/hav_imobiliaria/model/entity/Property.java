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
    private String property_description;
    @Column(nullable = false)
    private String property_type;
    @Column(nullable = false)
    private String purpose;
    @Column(nullable = false)
    private String property_status;
    @Column(nullable = false)
    private Double area;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Double promotional_price;
    @Column(nullable = false)
    private Boolean highlight;
    @Column(nullable = false)
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

    @OneToMany(mappedBy = "property")
    private List<Additionals> additionals;
}
