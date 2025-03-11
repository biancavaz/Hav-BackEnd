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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realtor")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")

public class RealtorController {

    private RealtorService service;

    //certo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RealtorPostRequestDTO createRealtor(
            @RequestBody @Valid RealtorPostRequestDTO realtorPostDTO) {
        return service.createRealtor(realtorPostDTO);
    }

    //certo
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Realtor editRealtor(
            @PathVariable Integer id,
            @RequestBody @Valid RealtorPutRequestDTO realtorPutDTO) {
        return service.editRealtor(id, realtorPutDTO);
    }


    //n é viável neste contexto
//    @PatchMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Realtor alterRealtor(
//            @PathVariable Integer id,
//            @RequestParam Integer idrealtor) {
//        return service.alterRealtor(id, idrealtor);
//    }

//
//    @GetMapping("/page")
//    public Page<Realtor> searchRealtors(
//            @PageableDefault(
//                    size = 10,
//                    sort = "saldo",
//                    direction = Sort.Direction.DESC,
//                    page = 0
//            ) Pageable pageable) {
//        return service.searchRealtors(pageable);
//    }

    @PostMapping("/filter")
    public Page<RealtorListGetResponseDTO> findByFilter(
            @RequestBody RealtorFilterPostResponseDTO realtorDTO,
            Pageable pageable){
        return service.findAllByFilter(realtorDTO, pageable);
    }
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public RealtorGetResponseDTO searchRealtor(@PathVariable Integer id) {
//        return service.searchRealtor(id);
//    }
//
//    //certo
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void removeRealtor(@PathVariable Integer id) {
//        service.removeRealtor(id);
//    }


}
