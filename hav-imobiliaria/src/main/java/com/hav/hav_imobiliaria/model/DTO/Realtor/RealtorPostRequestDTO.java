package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.DTO.User.UserPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.User.Realtor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RealtorPostRequestDTO(
        @NotNull UserPostRequestDTO userDTO,
        @NotBlank String creci
) {

    public Realtor convert() {
        return Realtor.builder()
                .name(userDTO.name())
                .email(userDTO.email())
                .password(userDTO.password())
                .cpf(userDTO.cpf())
                .celphone(userDTO.celphone())
                .phoneNumber(userDTO.phoneNumber())
                .birthDate(userDTO.birthDate())
                .address(userDTO.addressDTO().convert())
                .creci(creci)
                .build();
    }


}
