package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

public record RealtorPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String cpf,
        @NotBlank String celphone,
//        @NotBlank Date birthDate,
        @NotBlank String creci
) {

    public RealtorPostRequestDTO convertToDTO(Realtor realtor) {
        return new RealtorPostRequestDTO(
                realtor.getName(),
                realtor.getEmail(),
                realtor.getPassword(),
                realtor.getCpf(),
                realtor.getCelphone(),
                realtor.getCreci()
        );
    }


//    public Realtor convert(){
//        return Realtor.builder()
//                .name(name)
//                .email(email)
//                .password(password)
//                .creci(creci)
//                .build();
//    }
}
