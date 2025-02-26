package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.PropertyFeatureModel;
import com.hav.hav_imobiliaria.repository.PropertyFeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyFeatureService {

    private final PropertyFeatureRepository repository;

    public PropertyFeatureModel create(PropertyFeatureModel propertyFeatureModel) {
        return repository.save(propertyFeatureModel);
    }
}
