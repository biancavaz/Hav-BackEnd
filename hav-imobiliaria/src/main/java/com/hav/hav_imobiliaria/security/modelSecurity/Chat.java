package com.hav.hav_imobiliaria.security.modelSecurity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String chat_name;

    private String chat_image;

    @Column(name = "is_group")
    private boolean is_group;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserSecurity createdBy;

    @ManyToMany
    private Set<UserSecurity> users = new HashSet<>();

    @OneToMany
    private List<Message> messages = new ArrayList<>();
}
