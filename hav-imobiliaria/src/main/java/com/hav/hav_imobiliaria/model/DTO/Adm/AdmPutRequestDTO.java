package com.hav.hav_imobiliaria.model.DTO.Adm;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;

public record AdmPutRequestDTO(
        String name,
        String email,
        String password,
        String celphone,
        String phoneNumber,
        AddressPostRequestDTO address) {
}
