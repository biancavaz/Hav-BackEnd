package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record RealtorPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String cpf,
        @NotBlank String celphone,
        @NotBlank String creci,
        String phoneNumber,
        @NotNull Boolean archived,
        AddressPostRequestDTO address) {

    public RealtorPostRequestDTO convertToDTO(Realtor realtor) {
        return new RealtorPostRequestDTO(
                realtor.getName(),
                realtor.getEmail(),
                realtor.getCpf(),
                realtor.getCelphone(),
                realtor.getCreci(),
                realtor.getPhoneNumber(),
                realtor.getArchived(),
                address);
    }


}
