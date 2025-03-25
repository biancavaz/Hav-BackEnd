package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPutRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProprietorPutRequestDTO {
    @NotBlank
    String name;
    @NotBlank
    String email;
    @NotBlank
    String celphone;
    @NotBlank
    String phoneNumber;
    @Valid
    @NotNull
    AddressPutRequestDTO address;

}
