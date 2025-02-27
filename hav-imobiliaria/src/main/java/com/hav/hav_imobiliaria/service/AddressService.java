package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public Address create(Address propertyAddressModel) {
        return repository.save(propertyAddressModel);
    }
}
