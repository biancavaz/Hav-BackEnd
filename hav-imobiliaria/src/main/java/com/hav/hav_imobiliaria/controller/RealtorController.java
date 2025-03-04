package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Realtor;
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
public class RealtorController {

    private RealtorService service;

    // REALTOR
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.CREATED)
    public Realtor signinRealtor(
            @RequestBody @Valid RealtorPostRequestDTO realtorPostDTO){
        return service.createRealtor(realtorPostDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Realtor editRealtor(
            @PathVariable Integer id,
            @RequestBody @Valid RealtorPutRequestDTO realtorPutDTO){
        return service.editRealtor(id, realtorPutDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Realtor alterarRealtor(
            @PathVariable Integer id,
            @RequestParam Integer idrealtor){
        return service.alterRealtor(id, idrealtor);
    }

    @GetMapping("/page")
    public Page<Realtor> searchRealtors(
            @PageableDefault(
                    size = 10, //quantidade de itens por página
                    sort = "saldo", //o que vai ser listado
                    direction= Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
                    page= 0 //começa mostrando a página 0
            ) Pageable pageable){
        return service.searchRealtors(pageable);
    };

//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public RealtorGetResponseDTO searchRealtor(
//            @PathVariable Integer id){
//        Realtor realtor = service.searchRealtor(id);
//        return realtor.convert();
//    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRealtor(@PathVariable Integer id){
        service.remove(id);
    }


}
