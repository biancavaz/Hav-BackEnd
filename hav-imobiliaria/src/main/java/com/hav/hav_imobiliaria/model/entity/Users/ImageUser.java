package com.hav.hav_imobiliaria.model.entity.Users;

import com.hav.hav_imobiliaria.model.entity.Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "image_property")
@NoArgsConstructor
@AllArgsConstructor
public class ImageUser extends Image {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ImageUser(String s3Key, User user) {
        super(null, s3Key);
        this.user = user;
    }
}
