package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsGetRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import com.hav.hav_imobiliaria.repository.AdditionalsRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdditionalsService {

    private final AdditionalsRepository repository;
    private final ModelMapper modelMapper;

    public Additionals create(@Valid AdditionalsPostRequestDTO additionalsDTO) {

        Additionals additionals = modelMapper.map(additionalsDTO, Additionals.class);
        return repository.save(additionals);
    }

    public List<Additionals> findAllById(List<Integer> additionalsIds) {
        return repository.findAllById(additionalsIds);
    }


    public List<AdditionalsGetRequestDTO> findAllAdditionals() {

        return repository.findAll().stream().map(addtional -> modelMapper.map(addtional, AdditionalsGetRequestDTO.class)).toList();
    }
}
