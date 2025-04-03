package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.service.CustumerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustumerService service;



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerPostRequestDTO createCustomer(
            @RequestPart("customer") @Valid CustomerPostRequestDTO CustomerPostDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return service.createCustumer(CustomerPostDTO, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Customer updateCustomer(
            @PathVariable Integer id,
            @RequestBody @Valid CustomerPutRequestDTO custumerDTO,
            @RequestParam(value = "deletedImageId", required = false) @Positive Integer deletedImageId,
            @RequestPart(value = "newImage", required = false) MultipartFile newImage
    ) {
        return service.updateCustomer(id, custumerDTO, deletedImageId, newImage);
    }

//    @PatchMapping("/{id}")
//    @GetMapping("/page")
//    public Page<Customer> searchCustumerOwners(
//            @PageableDefault(
//                    size = 10, //quantidade de itens por página
//                    sort = "saldo", //o que vai ser listado
//                    direction = Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
//                    page = 0 //começa mostrando a página 0
//            ) Pageable pageable) {
//        return service.searchCustumers(pageable);
//    }

    @PostMapping("/filter")
    public Page<CustomerListGetResponseDTO> findByFilter(@RequestBody CustomerFilterPostResponseDTO customerDto, Pageable pageable) {
        return service.findAllByFilter(customerDto, pageable);
    }

    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> customerIds) {
        service.changeArchiveStatus(customerIds);
    }

//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Customer alterCustumerOwner(
//            @PathVariable Integer id,
//            @RequestParam Integer idCustumerOwner) {
//        return service.alterCustomer(id, idCustumerOwner);
//    }
//
//
//    @GetMapping("/page")
//    public Page<Customer> searchCustumerOwners(
//            @PageableDefault(
//                    size = 10, //quantidade de itens por página
//                    sort = "saldo", //o que vai ser listado
//                    direction = Sort.Direction.DESC, // tipo da ordem que vai ser mostrado
//                    page = 0 //começa mostrando a página 0
//            ) Pageable pageable) {
//        return service.searchCustumers(pageable);
//    }
//
//    ;
//

       @GetMapping("/{id}")
       @ResponseStatus(HttpStatus.OK)
       public CustomerPutRequestDTO searchRealtor(
               @PathVariable Integer id){
           CustomerPutRequestDTO customer = service.searchCustumer(id);
           return customer;
       }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void removeCustomerOwner(@PathVariable Integer id) {
//        service.removeCustumer(id);
//    }

    @DeleteMapping
    @RequestMapping
    public void removeCustomerList(@RequestBody List<Integer> idList) {
        service.removeList(idList);
    }
}
