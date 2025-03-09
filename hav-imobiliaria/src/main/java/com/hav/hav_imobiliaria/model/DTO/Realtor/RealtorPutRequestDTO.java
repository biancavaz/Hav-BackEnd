package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record RealtorPutRequestDTO(
        String email,
        String password,
        String celphone,
        String phoneNumber,
        Boolean archived,
        AddressPostRequestDTO address) {

}
