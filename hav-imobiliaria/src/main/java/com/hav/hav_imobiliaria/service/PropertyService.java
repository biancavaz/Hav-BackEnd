package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.PropertyModel;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;

    public PropertyModel create(PropertyModel propertyModel) {
        return repository.save(propertyModel);
    }
}
