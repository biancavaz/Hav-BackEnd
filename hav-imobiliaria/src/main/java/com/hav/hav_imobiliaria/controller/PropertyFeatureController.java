package com.hav.hav_imobiliaria.controller;

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
