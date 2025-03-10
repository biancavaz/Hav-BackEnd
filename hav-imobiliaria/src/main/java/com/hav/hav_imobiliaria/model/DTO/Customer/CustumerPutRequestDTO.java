package com.hav.hav_imobiliaria.model.DTO.Customer;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import jakarta.validation.constraints.NotBlank;

public record CustumerPutRequestDTO(
        String email,
        String password,
        String celphone,
        String phoneNumber,
        Boolean archived,
        AddressPostRequestDTO address) {

}
