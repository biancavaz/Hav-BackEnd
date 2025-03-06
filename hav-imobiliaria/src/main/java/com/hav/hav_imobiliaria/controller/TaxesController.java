package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Taxes;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxes")
@AllArgsConstructor
public class TaxesController {

    private TaxesService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Taxes create(@RequestBody @Valid TaxesPostRequestDTO taxesDTO) {
        return service.create(taxesDTO);
    }
}
