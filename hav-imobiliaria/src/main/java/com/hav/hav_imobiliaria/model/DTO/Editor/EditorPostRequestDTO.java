package com.hav.hav_imobiliaria.model.DTO.Editor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record EditorPostRequestDTO(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String cpf,
        @NotBlank String celphone,
        String phoneNumber,
        @NotNull Boolean archived,
        AddressPostRequestDTO address) {


    public EditorPostRequestDTO convertToDTO(Editor editor) {
        return new EditorPostRequestDTO(
                editor.getName(),
                editor.getEmail(),
                editor.getCpf(),
                editor.getCelphone(),
                editor.getPhoneNumber(),
                editor.getArchived(),
                address);
    }
}
