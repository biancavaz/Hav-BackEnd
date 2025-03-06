package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Additionals;
import com.hav.hav_imobiliaria.repository.AdditionalsRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdditionalsService {

    private final AdditionalsRepository repository;

    public Additionals create(@Valid AdditionalsPostRequestDTO additionalsDTO) {
        Additionals additionals = additionalsDTO.convert();
        return repository.save(additionals);
    }

    public List<Additionals> findAllyById(List<Integer> additionalsIds) {
        return repository.findAllById(additionalsIds);
    }
}
