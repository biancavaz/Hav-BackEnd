package com.hav.hav_imobiliaria.security.modelSecurity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @CurrentTimestamp
    private LocalDateTime createdAt;

    private Boolean isRead = false;

    @ManyToOne
    private UserSecurity user;

    @ManyToOne
    private Chat chat;

}
