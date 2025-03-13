package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.*;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.service.RealtorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/realtor")
@AllArgsConstructor

public class RealtorController {

    private RealtorService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RealtorPostRequestDTO createRealtor(
            @RequestBody @Valid RealtorPostRequestDTO realtorPostDTO) {
        return service.createRealtor(realtorPostDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Realtor editRealtor(
            @PathVariable Integer id,
            @RequestBody @Valid RealtorPutRequestDTO realtorPutDTO) {
        return service.editRealtor(id, realtorPutDTO);
    }


    @PostMapping("/filter")
    public Page<RealtorListGetResponseDTO> findByFilter(
            @RequestBody RealtorFilterPostResponseDTO realtorDTO,
            Pageable pageable){
        return service.findAllByFilter(realtorDTO, pageable);
    }

    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(
            @RequestBody List<Integer> realtorIds){
        service.changeArchiveStatus(realtorIds);
    }

    @DeleteMapping
    public void removeRealtorList(
            @RequestBody List<Integer> idList){

        service.removeList(idList);
    }

    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<RealtorPutRequestDTO> getRealtor(
            @PathVariable Integer id){

        RealtorPutRequestDTO realtorDTO = service.findRealtorById(id);
        return ResponseEntity.ok(realtorDTO);
    }
}
