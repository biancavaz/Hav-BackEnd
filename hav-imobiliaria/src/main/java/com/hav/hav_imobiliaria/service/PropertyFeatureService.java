package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.PropertyFeature;
import com.hav.hav_imobiliaria.repository.PropertyFeatureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyFeatureService {

    private final PropertyFeatureRepository repository;
//
//    public PropertyFeature create(PropertyFeaturePostRequestDTO propertyFeatureDTO) {
//        return repository.save(propertyFeatureDTO.convert());
//    }
}
