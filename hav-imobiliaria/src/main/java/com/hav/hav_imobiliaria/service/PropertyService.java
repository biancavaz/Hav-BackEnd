package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.Property;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;

    public Property create(Property propertyModel) {
        return repository.save(propertyModel);
    }
}
