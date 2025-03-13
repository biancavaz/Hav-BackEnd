package com.hav.hav_imobiliaria.model.DTO.Editor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPutRequestDTO;
import lombok.Data;

@Data
public class EditorPutRequestDTO{
        String name;
        String email;
        String password;
        String celphone;
        String phoneNumber;
        AddressPutRequestDTO address;

}
