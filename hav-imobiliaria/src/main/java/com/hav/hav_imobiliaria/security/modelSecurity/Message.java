package com.hav.hav_imobiliaria.security.modelSecurity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @CurrentTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    private UserSecurity user;

    @ManyToOne
    private Chat chat;

}
