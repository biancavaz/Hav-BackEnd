package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
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
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {


    private CustumerService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustumerPostRequestDTO createCustumer(
            @RequestBody @Valid CustumerPostRequestDTO CustumerPostDTO){
        System.out.println("Recebido: " + CustumerPostDTO);
        return service.createCustumerOwner(CustumerPostDTO);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer editCustumerOwner(
            @PathVariable Integer id,
            @RequestBody @Valid CustumerPutRequestDTO custumerOwnerDTO){
        return service.editCustumerOwner(id, custumerOwnerDTO);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer alterCustumerOwner(
            @PathVariable Integer id,
            @RequestParam Integer idCustumerOwner) {
        return service.alterCustomer(id, idCustumerOwner);
    }


    @GetMapping("/page")
    public Page<Customer> searchCustumerOwners(
            @PageableDefault(
                    size = 10, //quantidade de itens por página
                    sort = "saldo", //o que vai ser listado
                    direction = Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
                    page = 0 //começa mostrando a página 0
            ) Pageable pageable) {
        return service.searchCustumers(pageable);
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
        service.removeCustumer(id);
    }

}
