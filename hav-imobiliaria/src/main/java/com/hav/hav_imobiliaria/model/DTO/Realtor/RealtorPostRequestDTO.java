package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.validation.constraints.NotBlank;

public record RealtorPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String cpf,
        @NotBlank String phone,
        @NotBlank Date birthDate,
        @NotBlank String creci
) {

//    public Realtor convert(){
//        return Realtor.builder()
//                .name(name)
//                .email(email)
//                .password(password)
//                .creci(creci)
//                .build();
//    }
}
