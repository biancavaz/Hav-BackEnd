package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.service.CustumerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor

public class CustomerController {

    private CustumerService service;

    //certo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerPostRequestDTO createCustumer(
            @RequestBody @Valid CustomerPostRequestDTO CustumerPostDTO){
        System.out.println("Recebido: " + CustumerPostDTO);
        return service.createCustumer(CustumerPostDTO);
    }

    //certo
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer editCustumerOwner(
            @PathVariable Integer id,
            @RequestBody @Valid CustomerPutRequestDTO custumerDTO){
        return service.editCustumer(id, custumerDTO);
    }


    @PostMapping("/filter")
    public Page<CustomerListGetResponseDTO> findByFilter(@RequestBody CustomerFilterPostResponseDTO customerDto,
                                                         @RequestParam Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        System.out.printf(pageable.toString());
        return service.findAllByFilter(customerDto, pageable);
    }

    @PatchMapping("/changeArchiveStatus")
    public void changeArchiveStatus(@RequestBody List<Integer> customerIds){
        service.changeArchiveStatus(customerIds);
    }

    @DeleteMapping
    @RequestMapping
    public void removeCustomerList(@RequestBody List<Integer> idList){
            service.removeList(idList);
        }


    @GetMapping
    @RequestMapping("{id}")
    public ResponseEntity<CustomerPutRequestDTO> getCustomer(
            @PathVariable Integer id){

        CustomerPutRequestDTO customerDTO = service.findCustomerById(id);
        return ResponseEntity.ok(customerDTO);
    }

}
