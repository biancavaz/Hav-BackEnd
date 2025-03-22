package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Realtor.*;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.service.RealtorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/realtor")
@AllArgsConstructor
public class RealtorController {

    private RealtorService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RealtorPostRequestDTO createRealtor(
            @RequestPart("realtor") @Valid RealtorPostRequestDTO realtorPostDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return service.createRealtor(realtorPostDTO, image);
    }

//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Realtor editRealtor(
//            @PathVariable Integer id,
//            @RequestBody @Valid RealtorPutRequestDTO realtorPutDTO) {
//        return service.updateRealtor(id, realtorPutDTO);
//    }


    @PostMapping("/filter")
    public Page<RealtorListGetResponseDTO> findByFilter(
            @RequestBody RealtorFilterPostResponseDTO realtorDTO,
            @RequestParam Integer page){
        System.out.println(realtorDTO);
        Pageable pageable = PageRequest.of(page, 10);
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
    public RealtorPutRequestDTO getRealtor(
            @PathVariable Integer id){
        System.out.println("----------");
        RealtorPutRequestDTO realtorDTO = service.findRealtorById(id);
        System.out.println(realtorDTO);
        return realtorDTO;
    }
}
