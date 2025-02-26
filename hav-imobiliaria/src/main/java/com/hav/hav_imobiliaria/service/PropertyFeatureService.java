package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.PropertyFeature;
import com.hav.hav_imobiliaria.repository.PropertyFeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyFeatureService {

    private final PropertyFeatureRepository repository;

    public PropertyFeature create(PropertyFeature propertyFeatureModel) {
        return repository.save(propertyFeatureModel);
    }
}
