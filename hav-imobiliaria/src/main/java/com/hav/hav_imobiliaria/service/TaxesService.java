package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.Taxes;
import com.hav.hav_imobiliaria.repository.TaxesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxesService {

    private final TaxesRepository repository;

    public Taxes create(Taxes taxesModel) {
        return repository.save(taxesModel);
    }
}
