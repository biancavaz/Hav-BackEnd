package com.hav.hav_imobiliaria.model.DTO.Customer;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;

public record CustomerPutRequestDTO(
        String name,
        String email,
        String password,
        String celphone,
        String phoneNumber,
        AddressPostRequestDTO address) {

}
