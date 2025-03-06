package com.hav.hav_imobiliaria.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Property;
import com.hav.hav_imobiliaria.service.PropertyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/property")
@AllArgsConstructor
public class PropertyController {

    private final PropertyService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Property create(@RequestBody @Valid PropertyPostRequestDTO propertyDTO) {
        return service.create(propertyDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PropertyGetResponseDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void delete(@Positive @NotNull Integer id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Property modifyProperty(@Positive @NotNull @PathVariable Integer id,
                                   @Valid PropertyPutRequestDTO propertyDTO) {
        return service.modifyProperty(id, propertyDTO);
    }
}
