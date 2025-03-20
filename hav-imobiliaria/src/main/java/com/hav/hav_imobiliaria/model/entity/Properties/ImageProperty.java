package com.hav.hav_imobiliaria.model.entity.Properties;

import com.hav.hav_imobiliaria.model.entity.Image;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "image_property")
@NoArgsConstructor
@AllArgsConstructor
public class ImageProperty extends Image {

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    public ImageProperty(String s3Key, Property property) {
        super(null, s3Key);
        this.property = property;
    }
}
