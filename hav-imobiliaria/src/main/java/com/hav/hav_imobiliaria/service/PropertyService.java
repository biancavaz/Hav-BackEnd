package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.dto.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Additionals;
import com.hav.hav_imobiliaria.model.entity.Property;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;
    private final AdditionalsService additionalsService;

    public Property create(@Valid PropertyPostRequestDTO propertyDTO) {

        Property property = propertyDTO.convert();

        List<Additionals> additionals = additionalsService.findAllyId(propertyDTO.additionalsId());

        property.setAdditionals(additionals);

        return repository.save(property);
    }
}
