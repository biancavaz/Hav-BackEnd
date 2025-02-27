package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.dto.AdditionalsPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Additionals;
import com.hav.hav_imobiliaria.repository.AdditionalsRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdditionalsService {

    private final AdditionalsRepository repository;

    public Additionals create(@Valid AdditionalsPostRequestDTO additionalsDTO) {
        Additionals additionals = additionalsDTO.convert();
        return repository.save(additionals);
    }
}
