package com.hav.hav_imobiliaria.model.DTO.Adm;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record AdmPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String cpf,
        @NotBlank String celphone,
        @NotNull Date birthDate,
        String phoneNumber,
        @NotNull Boolean archived,
        AddressPostRequestDTO address) {


    public AdmPostRequestDTO convertToDTO(Adm adm) {
        return new AdmPostRequestDTO(
                adm.getName(),
                adm.getEmail(),
                adm.getPassword(),
                adm.getCpf(),
                adm.getCelphone(),
                adm.getBirthDate(),
                adm.getPhoneNumber(),
                adm.getArchived(),
                address);
    }
}
