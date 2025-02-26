package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.TaxesModel;
import com.hav.hav_imobiliaria.repository.TaxesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxesService {

    private final TaxesRepository repository;

    public TaxesModel create(TaxesModel taxesModel) {
        return repository.save(taxesModel);
    }
}
