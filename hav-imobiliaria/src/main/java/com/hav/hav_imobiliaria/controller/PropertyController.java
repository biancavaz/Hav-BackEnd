package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.DTO.Property.*;
import com.hav.hav_imobiliaria.service.PropertyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //endpoint para filtragem de imóveis
    @GetMapping("/filter")
    public Page<PropertyListGetResponseDTO> findByFilter(@RequestBody PropertyFilterPostResponseDTO propertyDto, Pageable pageable){
        return service.findAllByFilter(propertyDto, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Positive @NotNull Integer id) {
        service.delete(id);
    }

    @DeleteMapping("/code/{propertyCode}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteByPropertyCode(@PathVariable @NotNull String propertyCode) {
        service.deleteByPropertyCode(propertyCode);
    }

    @DeleteMapping("/code")
    @ResponseStatus(HttpStatus.OK)
    public void deletePropertiesByPropertyCode(@RequestParam @NotNull List<String> propertyCodes) {
        service.deletePropertiesByPropertyCode(propertyCodes);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Property modifyProperty(@Positive @NotNull @PathVariable Integer id,
                                   @Valid PropertyPutRequestDTO propertyDTO) {
        return service.modifyProperty(id, propertyDTO);
    }
}
