package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.repository.PropertyAddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyAddressService {

    private final PropertyAddressRepository repository;

    public Address create(Address propertyAddressModel) {
        return repository.save(propertyAddressModel);
    }
}
