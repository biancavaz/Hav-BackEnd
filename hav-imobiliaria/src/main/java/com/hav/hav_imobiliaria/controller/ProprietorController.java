package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.service.ProprietorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proprietor")
@AllArgsConstructor
public class ProprietorController {


    private final ProprietorService service;


//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Proprietor createProprietor(
//            @RequestBody @Valid ProprietorPostDTO proprietorDTO){
//        return service.createProprietor(proprietorDTO);
//    }
//
//    @PutMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Proprietor editProprietor(
//            @PathVariable Integer id,
//            @RequestBody @Valid ProprietorPutRequestDTO ProprietorPutDTO){
//        return service.editProprietor(id, ProprietorPutDTO);
//    }
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
}
