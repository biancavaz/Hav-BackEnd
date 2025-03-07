package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

public record RealtorPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String cpf,
        @NotBlank String celphone,
        @NotNull  Date birthDate,
        @NotBlank String creci,
        String phoneNumber,
        @NotNull Boolean archived,
        AddressPostRequestDTO address
) {

    public RealtorPostRequestDTO convertToDTO(Realtor realtor) {
        return new RealtorPostRequestDTO(
                realtor.getName(),
                realtor.getEmail(),
                realtor.getPassword(),
                realtor.getCpf(),
                realtor.getCelphone(),
                realtor.getBirthDate(),
                realtor.getCreci(),
                realtor.getPhoneNumber(),
                realtor.getArchived(),
                address);
    }


}
