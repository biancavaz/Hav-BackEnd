package com.hav.hav_imobiliaria.model.entity.Users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "image_user")
@NoArgsConstructor
@AllArgsConstructor
public class ImageUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String s3Key;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public ImageUser(String s3Key, User user) {
        this.s3Key = s3Key;
        this.user = user;
    }
}
