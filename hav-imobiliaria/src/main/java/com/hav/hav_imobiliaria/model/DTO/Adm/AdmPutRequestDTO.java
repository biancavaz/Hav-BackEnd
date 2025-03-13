
package com.hav.hav_imobiliaria.model.DTO.Adm;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPutRequestDTO;
import lombok.Data;

@Data
public class AdmPutRequestDTO{
    String cpf;
        String name;
        String email;
        String celphone;
        String phoneNumber;
        AddressPutRequestDTO address;
}
