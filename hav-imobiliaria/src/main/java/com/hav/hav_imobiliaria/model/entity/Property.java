package com.hav.hav_imobiliaria.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "property_description", nullable = false)
    private String propertyDescription;

    @Column(name = "property_type", nullable = false)
    private String propertyType;

    @Column(nullable = false)
    private String purpose;

    @Column(name = "property_status", nullable = false)
    private String propertyStatus;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private Double price;

    @Column(name = "promotional_price", nullable = false)
    private Double promotionalPrice;

    @Column(nullable = false)
    private Boolean highlight;

    @Column(name = "property_category", nullable = false)
    private String propertyCategory;

    @CreationTimestamp
    @JsonIgnore
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;

    @OneToOne
    @JoinColumn(name = "id_taxes", nullable = false)
    private Taxes taxes;

    @OneToOne
    @JoinColumn(name = "id_property_feature", nullable = false)
    private PropertyFeature propertyFeatures;

    @ManyToMany
    @JoinTable(
            name = "property_additionals",
            joinColumns = @JoinColumn(name = "id_property"),
            inverseJoinColumns = @JoinColumn(name = "id_additional")
    )
    private List<Additionals> additionals;

}
