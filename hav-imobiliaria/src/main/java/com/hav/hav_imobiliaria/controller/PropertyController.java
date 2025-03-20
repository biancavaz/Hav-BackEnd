package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPutRequestDTO;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/property")
@AllArgsConstructor
public class PropertyController {

    private final PropertyService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Property create(
            @RequestPart("property") @Valid PropertyPostRequestDTO propertyDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        return service.create(propertyDTO, images);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PropertyGetResponseDTO> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @PostMapping("/filter")
    public Page<PropertyListGetResponseDTO> findByFilter(@RequestBody PropertyFilterPostResponseDTO propertyDto,
                                                         @RequestParam  Integer page) {
        Pageable pageable = PageRequest.of(page, 10);

        return service.findAllByFilter(propertyDto, pageable);
    }

    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> propertyIds) {
        service.changeArchiveStatus(propertyIds);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Positive @NotNull Integer id) {
        service.delete(id);
    }

    @DeleteMapping
    @RequestMapping
    public void removePropertyList(@RequestBody List<Integer> idList) {
        System.out.println(idList);
        service.removeList(idList);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Property modifyProperty(@Positive @NotNull @PathVariable Integer id,
                                   @Valid PropertyPutRequestDTO propertyDTO) {
        return service.modifyProperty(id, propertyDTO);
    }
    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<PropertyPutRequestDTO> getProperty(
            @PathVariable Integer id){

        PropertyPutRequestDTO propertyDto = service.findPropertyById(id);
        return ResponseEntity.ok(propertyDto);
    }
}
