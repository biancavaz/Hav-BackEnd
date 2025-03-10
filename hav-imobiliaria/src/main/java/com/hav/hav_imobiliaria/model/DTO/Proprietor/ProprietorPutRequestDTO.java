package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;

public record ProprietorPutRequestDTO(
        String name,
        String email,
        String celphone,
        String phoneNumber,
        Boolean archived,
        AddressPostRequestDTO address) {

}
