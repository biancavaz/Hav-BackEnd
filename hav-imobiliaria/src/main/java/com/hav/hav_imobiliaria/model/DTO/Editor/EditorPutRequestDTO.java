package com.hav.hav_imobiliaria.model.DTO.Editor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;

public record EditorPutRequestDTO(
        String email,
        String password,
        String celphone,
        String phoneNumber,
        Boolean archived,
        AddressPostRequestDTO address) {

}
