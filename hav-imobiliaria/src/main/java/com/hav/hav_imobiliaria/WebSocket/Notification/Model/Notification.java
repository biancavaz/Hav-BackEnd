package com.hav.hav_imobiliaria.WebSocket.Notification.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToMany(mappedBy = "notifications")
    @JsonIgnore
    private List<User> recipient;
    private String title;
    private String content;
    @Column(name = "is_read")
    private Boolean read;
    private LocalDateTime dataEnvio = LocalDateTime.now();

}
