package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsGetRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import com.hav.hav_imobiliaria.service.AdditionalsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/additionals")
@AllArgsConstructor
public class AdditionalsController {

    private final AdditionalsService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Additionals create(@RequestBody @Valid AdditionalsPostRequestDTO additionalsDTO) {
        return service.create(additionalsDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdditionalsGetRequestDTO> getAll() {
        return service.findAllAdditionals();
    }
}
