package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.CustumerOwner.CustomerOwnerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.CustumerOwner.CustomerOwnerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
import com.hav.hav_imobiliaria.service.CustomerOwnerService;
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
public class CustumerOwnerController {


    private CustomerOwnerService service;

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOwner signinCustumerOwner(
            @RequestBody @Valid CustomerOwnerPostRequestDTO CustumerOwnerPostDTO) {
        return service.createCustomerOwner(CustumerOwnerPostDTO);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOwner editCustumerOwner(
            @PathVariable Integer id,
            @RequestBody @Valid CustomerOwnerPutRequestDTO custumerOwnerDTO) {
        return service.editCustomerOwner(id, custumerOwnerDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOwner alterCustomerOwner(
            @PathVariable Integer id,
            @RequestParam Integer idCustumerOwner) {
        return service.alterCustomerOwner(id, idCustumerOwner);
    }


    @GetMapping("/page")
    public Page<CustomerOwner> searchCustumerOwners(
            @PageableDefault(
                    size = 10, //quantidade de itens por página
                    sort = "saldo", //o que vai ser listado
                    direction = Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
                    page = 0 //começa mostrando a página 0
            ) Pageable pageable) {
        return service.searchCustomerOwners(pageable);
    }

    ;

//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public CustumerOwnerGetRequestDTO searchRealtor(
//            @PathVariable Integer id){
//        Realtor realtor = service.searchCustumerOwner(id);
//        return realtor.convert();
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCustomerOwner(@PathVariable Integer id) {
        service.removeCustomerOwner(id);
    }

}
