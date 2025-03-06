package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.repository.AddressRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public Address create(@Valid AddressPostRequestDTO addressDTO) {
        return repository.save(addressDTO.convert());
    }

    public Address findById(@Positive @NotNull Integer id) {
        return repository.findById(id).get();
    }
}
