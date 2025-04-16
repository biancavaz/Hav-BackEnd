package com.hav.hav_imobiliaria.model.entity.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hav.hav_imobiliaria.WebSocket.Notification.Model.Notification;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED) // herança
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String cpf;

    @Column()
    private String celphone;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean archived = false;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private Address address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ImageUser imageUser;

    @ManyToMany
    @JoinTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    @JsonIgnore
    private List<Property> properties;

    public List<Property> getProperties() {
        if(properties != null){
            return properties;
        }
        else{
            return new ArrayList<>();
        }
    }
    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name = "user_notifications",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    private List<Notification> notifications = new ArrayList<>();

    public @NotNull boolean getArchived() {
        return this.archived;
    }
    public void changeArchiveStatus(){
        this.archived = !this.archived;
    }
    public Object getUserType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserType'");
    }
    public void setUserType(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUserType'");
    }

}
