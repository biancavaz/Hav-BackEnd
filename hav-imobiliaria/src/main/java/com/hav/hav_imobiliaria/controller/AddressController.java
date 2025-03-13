package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.service.AddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService service;


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Address editAddress( @NotNull @Positive Integer id,
                               @RequestBody @Valid AddressPutRequestDTO addressDTO){
        return service.editAddress(id, addressDTO);
    }

}
