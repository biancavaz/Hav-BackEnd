package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class RealtorPutRequestDTO{
        String name;
        String email;
        String celphone;
        String phoneNumber;
        AddressPutRequestDTO address;

}
