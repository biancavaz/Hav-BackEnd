package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.service.AdmService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adm")
@AllArgsConstructor

public class AdmController {

    private final AdmService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdmPostRequestDTO createAdm(
            @RequestBody @Valid AdmPostRequestDTO admPostDTO){
        return service.createAdm(admPostDTO);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Adm editAdm(
            @PathVariable Integer id,
            @RequestBody @Valid AdmPutRequestDTO admPutDTO){
        return service.editAdm(id, admPutDTO);
    }

    @PostMapping("/filter")
    public Page<AdmListGetResponseDTO> findByFilter(@RequestBody AdmFilterPostResponseDTO admDto, Pageable pageable){
        return service.findAllByFilter(pageable, admDto);
    }
    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> admIds){
        service.changeArchiveStatus(admIds);
    }


//
//
//    @PatchMapping
//    @ResponseStatus(HttpStatus.OK)
//    public Adm alterarAdm(
//            @PathVariable Integer id,
//            @RequestParam Integer idAdm){
//        return service.alterAdm(id, idAdm);
//    }
//
//
//    @GetMapping
//    public Page<Adm> searchAdm(
//            @PageableDefault(
//                    size = 10, //quantidade de itens por página
//                    sort = "saldo", //o que vai ser listado
//                    direction= Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
//                    page= 0 //começa mostrando a página 0
//            ) Pageable pageable){
//        return service.searchAdm(pageable);
//    };
//
//
//    //    @GetMapping
////    @RequestMapping("/{id}")
////    public RealtorGetResponseDTO searchRealtor(
//    ////            @PathVariable Integer id){
//    ////        Realtor realtor = service.searchRealtor(id);
////        return realtor.convert();
////    }
//
//    @DeleteMapping
//    @RequestMapping("/{id}")
//    public void removeAdm(@PathVariable Integer id){
//        service.remove(id);
//    }

    @DeleteMapping
    @RequestMapping
    public void removeAdmList(@RequestBody List<Integer> idList){
        service.removeList(idList);
    }

}
