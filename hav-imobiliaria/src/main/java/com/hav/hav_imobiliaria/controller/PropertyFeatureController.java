package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.dto.PropertyFeaturePostRequestDTO;
import com.hav.hav_imobiliaria.service.PropertyFeatureService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/propertyfeature")
@AllArgsConstructor
public class PropertyFeatureController {

    private final PropertyFeatureService service;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public PropertyFeature create(@RequestBody @Valid PropertyFeaturePostRequestDTO propertyFeatureDTO) {
//        return service.create(propertyFeatureDTO);
//    }
}
