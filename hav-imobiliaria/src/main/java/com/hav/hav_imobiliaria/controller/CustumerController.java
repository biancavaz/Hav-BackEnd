package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Custumer.CustumerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Custumer.CustumerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Custumer;
import com.hav.hav_imobiliaria.service.CustumerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/custumerOwner")
@AllArgsConstructor
public class CustumerController {


    private CustumerService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Custumer signinCustumerOwner(
            @RequestBody @Valid CustumerPostRequestDTO CustumerOwnerPostDTO){
        return service.createCustumerOwner(CustumerOwnerPostDTO);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Custumer editCustumerOwner(
            @PathVariable Integer id,
            @RequestBody @Valid CustumerPutRequestDTO custumerOwnerDTO){
        return service.editCustumerOwner(id, custumerOwnerDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Custumer alterCustumerOwner(
            @PathVariable Integer id,
            @RequestParam Integer idCustumerOwner){
        return service.alterCustumerOwner(id, idCustumerOwner);
    }


    @GetMapping("/page")
    public Page<Custumer> searchCustumerOwners(
            @PageableDefault(
                    size = 10, //quantidade de itens por página
                    sort = "saldo", //o que vai ser listado
                    direction= Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
                    page= 0 //começa mostrando a página 0
            ) Pageable pageable){
        return service.searchCustumerOwners(pageable);
    };

//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CustumerOwnerGetRequestDTO searchRealtor(
//            @PathVariable Integer id){
//        Realtor realtor = service.searchCustumerOwner(id);
//        return realtor.convert();
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCustumerOwner(@PathVariable Integer id){
        service.removeCustumerOwner(id);
    }

}
