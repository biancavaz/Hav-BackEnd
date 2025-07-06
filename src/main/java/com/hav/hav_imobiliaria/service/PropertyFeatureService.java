package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.PropertyFeature;
import com.hav.hav_imobiliaria.repository.PropertyFeatureRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyFeatureService {

    private final PropertyFeatureRepository repository;
    private final ModelMapper modelMapper;

    public PropertyFeature create(
            @Valid PropertyFeaturePostRequestDTO propertyFeatureDTO) {

        PropertyFeature propertyFeature =
                modelMapper.map(propertyFeatureDTO, PropertyFeature.class);

        return repository.save(propertyFeature);
    }
}
