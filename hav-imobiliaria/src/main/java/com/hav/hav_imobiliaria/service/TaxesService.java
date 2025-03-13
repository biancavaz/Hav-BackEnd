package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Taxes;
import com.hav.hav_imobiliaria.repository.TaxesRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxesService {

    private final TaxesRepository repository;

    private final ModelMapper modelMapper;

    public Taxes create(@Valid TaxesPostRequestDTO taxesDTO) {
        Taxes taxes = modelMapper.map(taxesDTO, Taxes.class);
        return repository.save(taxes);
    }
}
