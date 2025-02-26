package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.Additionals;
import com.hav.hav_imobiliaria.repository.AdditionalsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdditionalsService {

    private final AdditionalsRepository repository;

    public Additionals create(Additionals additionalsModel) {
        return repository.save(additionalsModel);
    }
}
