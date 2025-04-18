package com.hav.hav_imobiliaria.model.entity.Properties;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Column(name = "property_code", nullable = false, unique = true)
    private String propertyCode;

    @Column(nullable = false)
    private String title;

    @Column(name = "property_description", nullable = false)
    private String propertyDescription;

    @Column(name = "floors", nullable = false)
    private Integer floors;

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

//    @Column(name = "property_category", nullable = false)
//    private String propertyCategory;

    @CreationTimestamp
    @JsonIgnore
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean archived;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_taxes", nullable = false)
    @JsonManagedReference
    private Taxes taxes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_property_feature", nullable = false)
    @JsonManagedReference
    private PropertyFeature propertyFeatures;

    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "property_additionals",
            joinColumns = @JoinColumn(name = "id_property"),
            inverseJoinColumns = @JoinColumn(name = "id_additional")
    )
    private List<Additionals> additionals;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "id_proprietor")
    private Proprietor proprietor;

    @JsonManagedReference
    @ManyToMany()
    @JoinTable(
            name = "property_realtors",
            joinColumns = @JoinColumn(name = "id_property"),
            inverseJoinColumns = @JoinColumn(name = "id_realtor")
    )
    private List<Realtor> realtors;

    @ManyToMany(mappedBy = "properties")
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ImageProperty> imageProperties;

    public void changeArchiveStatus() {
        this.archived = !this.archived;
    }

    public List<Realtor> getRealtors() {
        if (realtors != null) {
            System.out.println(realtors);
            return realtors;
        }
        return new ArrayList<>();
    }

    public String getPreferredPropertyType(User user) {
        List<Property> properties = user.getProperties();
        if (properties == null || properties.isEmpty()) {
            return null;
        }
        return user.getProperties().stream()
                .collect(Collectors.groupingBy(Property::getPropertyType, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue()) // tipo mais frequente
                .map(Map.Entry::getKey)
                .orElse(null);
    }

}

