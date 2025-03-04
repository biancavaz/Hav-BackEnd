package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.Properties.Taxes;
import com.hav.hav_imobiliaria.repository.TaxesRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxesService {

    private final TaxesRepository repository;
//
//    public Taxes create(@Valid TaxesPostRequestDTO taxesDTO) {
//        return repository.save(taxesDTO.convert());
//    }
}
