package com.hav.hav_imobiliaria.model.DTO.Realtor;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class RealtorGetResponseDTO {
    String cpf;
    String name;
    String email;
    String celphone;
    String creci;


    public RealtorGetResponseDTO(String name, String email, String cpf, String celphone, String creci) {
    }
}
