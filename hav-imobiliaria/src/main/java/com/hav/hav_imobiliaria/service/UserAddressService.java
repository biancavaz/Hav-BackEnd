package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.UserAddressModel;
import com.hav.hav_imobiliaria.repository.UserAddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAddressService {

    private final UserAddressRepository repository;

    public UserAddressModel create(UserAddressModel userAddressModel) {
        return repository.save(userAddressModel);
    }
}
