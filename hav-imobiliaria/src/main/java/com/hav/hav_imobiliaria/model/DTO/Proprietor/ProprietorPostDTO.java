package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public record ProprietorPostDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotNull String celphone,
        @NotNull Date birthDate,
        @Pattern(regexp = "^(\\d{11})?$") String cpf,
        @Pattern(regexp = "^(\\d{14})?$") String cnpj,
        String phoneNumber,
        @NotNull Boolean archived,
        AddressPostRequestDTO address
) {



    public ProprietorPostDTO convertToDTO(Proprietor proprietor) {
        return new ProprietorPostDTO(
                proprietor.getName(),
                proprietor.getEmail(),
                proprietor.getCelphone(),
                proprietor.getBirthDate(),
                proprietor.getCpf(),
                proprietor.getCnpj(),
                proprietor.getPhoneNumber(),
                proprietor.getArchived(),
                address);
    }
}
