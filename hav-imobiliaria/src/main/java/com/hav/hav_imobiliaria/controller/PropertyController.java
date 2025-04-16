package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTOwithId;
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
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetSpecificResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/property")
@AllArgsConstructor
public class PropertyController {

    private final PropertyService service;


    @GetMapping("/randomHighlights")
    public List<PropertyCardGetResponseDTO> getRandomHighlights(){
        return service.findRandomHighlights();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyListGetResponseDTO create(
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
                                                         @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return service.findAllByFilter(propertyDto, pageable);
    }
    @PostMapping("/filter/card")
    public Page<PropertyCardGetResponseDTO> findByFilterCard(@RequestBody PropertyFilterPostResponseDTO propertyDto,
                                                         @RequestParam Integer page) {
        Pageable pageable = PageRequest.of(page, 12);
        return service.findAllByFilterCard(propertyDto, pageable);
    }
    @GetMapping("/filter/map")
    public List<PropertyMapGetResponseDTO> findByFilterMap() {
        return service.findAllByFilterMap();
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
        service.removeList(idList);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Property updateProperty(
            @PathVariable @Positive @NotNull Integer id,
            @RequestPart("property") @Valid PropertyPutRequestDTO propertyDTO,
            @RequestPart(value = "deletedImageIds", required = false) List<Integer> deletedImageIds,
            @RequestPart(value = "newImages", required = false) List<MultipartFile> newImages
    ) {
        System.out.println(propertyDTO.getAddress());
        return service.updateProperty(id, propertyDTO, deletedImageIds, newImages);
    }

    @GetMapping
    @RequestMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PropertyPutRequestDTO getProperty(
            @PathVariable Integer id) {
        return service.findPropertyById(id);
    }

    @GetMapping
    @RequestMapping("/propertyspecific/{id}")
    public ResponseEntity<PropertyGetSpecificResponseDTO> getPropertySpecific(
            @PathVariable Integer id) {
        PropertyGetSpecificResponseDTO propertyGetSpecificRequestDTO = service.findPropertySpecificById(id);
        return ResponseEntity.ok(propertyGetSpecificRequestDTO);
    }

    @GetMapping("/card")
    @ResponseStatus(HttpStatus.OK)
    public Page<PropertyCardGetResponseDTO> findPropertyCard(Pageable pageable) {
        return service.findPropertyCard(pageable);
    }


    //returns all the realtors of a property
    @GetMapping
    @RequestMapping("/realtorProperty/{id}")
    public List<RealtorGetResponseDTOwithId> getRealtorsOfProperty(@PathVariable Integer id) {
        return service.findRealtorsByPropertyId(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public Long getAllRegistredNumber(){
        return service.getAllRegistredNumber();
    }

    @GetMapping("/getPercentageRental")
    @ResponseStatus(HttpStatus.OK)
    public double getPercentageOfRentalProperties(){
        return service.getPercentageOfRentalProperties();
    }

    @GetMapping("/getPercentageForSale")
    @ResponseStatus(HttpStatus.OK)
    public double getPercentageOfForSaleProperties(){
        return service.getPercentageOfForSaleProperties();
    }

    @GetMapping("/getPercentageOfArchiveStatus")
    @ResponseStatus(HttpStatus.OK)
    public double getPercentageOfArchiveStatus(){
        return service.getPercentageOfArchiveStatus();
    }

    @GetMapping("/getQuantityOfRentalProperties")
    @ResponseStatus(HttpStatus.OK)
    public long getQuantityOfRentalProperties(){
        return service.getQuantityOfRentalProperties();
    }

    @GetMapping("/getQuantityOfForSaleProperties")
    @ResponseStatus(HttpStatus.OK)
    public long getQuantityOfForSaleProperties(){
        return service.getQuantityOfForSaleProperties();
    }

    @GetMapping("/getQuantityOfArchivedProperties")
    @ResponseStatus(HttpStatus.OK)
    public long getQuantityOfArchivedProperties(){
        return service.getQuantityOfArchivedProperties();
    }
}
