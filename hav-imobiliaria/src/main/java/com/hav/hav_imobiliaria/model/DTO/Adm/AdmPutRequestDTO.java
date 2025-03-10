package com.hav.hav_imobiliaria.model.DTO.Adm;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;

public record AdmPutRequestDTO(
        String email,
        String password,
        String celphone,
        String phoneNumber,
        Boolean archived,
        AddressPostRequestDTO address) {
}
