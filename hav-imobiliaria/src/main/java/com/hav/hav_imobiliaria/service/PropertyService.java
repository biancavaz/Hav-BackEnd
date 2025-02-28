package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;

    public Property create(Property propertyModel) {
        return repository.save(propertyModel);
    }

    public Page<Property> searchProperty(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
