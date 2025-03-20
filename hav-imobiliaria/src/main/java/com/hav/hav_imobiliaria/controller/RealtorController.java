package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Realtor.*;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.service.RealtorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
            Pageable pageable) {
        System.out.println("primeiro");
        System.out.println(realtorDTO.toString());
        return service.findAllByFilter(realtorDTO, pageable);
    }

    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> realtorIds) {
        service.changeArchiveStatus(realtorIds);
    }

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

    @DeleteMapping
    @RequestMapping
    public void removeRealtorList(@RequestBody List<Integer> idList) {
        service.removeList(idList);
    }

}
