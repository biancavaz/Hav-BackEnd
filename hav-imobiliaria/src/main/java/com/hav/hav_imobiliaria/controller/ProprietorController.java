package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPostDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.service.ProprietorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proprietor")
@AllArgsConstructor
public class ProprietorController {


    private final ProprietorService service;


    //certo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProprietorPostDTO createProprietor(
            @RequestBody @Valid ProprietorPostDTO proprietorDTO){
        return service.createProprietor(proprietorDTO);
    }


    //certo
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Proprietor editProprietor(
            @PathVariable Integer id,
            @RequestBody @Valid ProprietorPutRequestDTO ProprietorPutDTO){
        return service.editProprietor(id, ProprietorPutDTO);
    }


    @PostMapping("/filter")
    public Page<ProprietorListGetResponseDTO> findByFilter(@RequestBody ProprietorFilterPostResponseDTO proprietorDto, Pageable pageable){
        return service.findAllByFilter(pageable, proprietorDto);
    }
    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> proprietorIds){
        service.changeArchiveStatus(proprietorIds);
    }

//
//
//
//    @PatchMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Proprietor alterarProprietor(
//            @PathVariable Integer id,
//            @RequestParam Integer idProprietor){
//        return service.alterProprietor(id, idProprietor);
//    }
//
//    @GetMapping
//    public Page<Proprietor> searchProprietor(
//            @PageableDefault(
//                    size = 10, //quantidade de itens por página
//                    sort = "saldo", //o que vai ser listado
//                    direction= Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
//                    page= 0 //começa mostrando a página 0
//            ) Pageable pageable){
//        return service.searchProprietor(pageable);
//    };
//
////    @GetMapping
////    @RequestMapping("/{id}")
////    public RealtorGetResponseDTO searchRealtor(
//////            @PathVariable Integer id){
//////        Realtor realtor = service.searchRealtor(id);
////        return realtor.convert();
////    }
//
//
//
//    @DeleteMapping
//    @RequestMapping("/{id}")
//    public void removeProprietor(@PathVariable Integer id){
//        service.remove(id);
//    }
    @DeleteMapping
    @RequestMapping
    public void removeProprietorList(@RequestBody List<Integer> idList){
        service.removeList(idList);
    }
}
