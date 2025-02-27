package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.dto.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Property;
import com.hav.hav_imobiliaria.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/property")
@AllArgsConstructor
public class PropertyController {

    private final PropertyService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Property create(@RequestBody PropertyPostRequestDTO propertyDTO) {
        return service.create(propertyDTO);
    }
}
