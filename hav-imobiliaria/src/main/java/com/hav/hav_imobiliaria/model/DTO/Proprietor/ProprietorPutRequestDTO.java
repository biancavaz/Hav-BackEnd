package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPutRequestDTO;
import lombok.Data;

@Data
public class ProprietorPutRequestDTO{
        String name;
        String email;
        String celphone;
        String phoneNumber;
        AddressPutRequestDTO address;

}
