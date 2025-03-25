package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPostDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.service.ProprietorService;
import jakarta.validation.Valid;
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
@RequestMapping("/proprietor")
@AllArgsConstructor
public class ProprietorController {

    private final ProprietorService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProprietorPostDTO createProprietor(
            @RequestPart("proprietor") @Valid ProprietorPostDTO proprietorDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return service.createProprietor(proprietorDTO, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Proprietor updateProprietor(
            @PathVariable Integer id,
            @RequestBody @Valid ProprietorPutRequestDTO proprietorPutDTO,
            @RequestParam(value = "deletedImageId", required = false) @Positive Integer deletedImageId,
            @RequestPart(value = "newImage", required = false) MultipartFile newImage
    ) {
        return service.updateProprietor(id, proprietorPutDTO, deletedImageId, newImage);
    }

    @PostMapping("/filter")
    public Page<ProprietorListGetResponseDTO> findByFilter(@RequestBody ProprietorFilterPostResponseDTO proprietorDto,
                                                           @RequestParam Integer page) {
        System.out.println(proprietorDto);
        Pageable pageable = PageRequest.of(page, 10);
        return service.findAllByFilter(pageable, proprietorDto);
    }

    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> proprietorIds) {
        service.changeArchiveStatus(proprietorIds);
    }

    @DeleteMapping
    @RequestMapping
    public void removeProprietorList(@RequestBody List<Integer> idList) {
        service.removeList(idList);
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<ProprietorPutRequestDTO> getRealtor(
            @PathVariable Integer id) {

        ProprietorPutRequestDTO proprietorDTO = service.findProprietorById(id);
        return ResponseEntity.ok(proprietorDTO);
    }

}
