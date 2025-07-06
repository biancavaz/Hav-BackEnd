package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ProprietorGetResponseDTO {

    private String name;
    private String email;
    private String celphone;

    public ProprietorGetResponseDTO() {
    }

    public ProprietorGetResponseDTO(String name, String email, String celphone) {
        this.name = name;
        this.email = email;
        this.celphone = celphone;
    }

    // Getters e setters...
}

