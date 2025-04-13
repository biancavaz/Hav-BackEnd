package com.hav.hav_imobiliaria.model.DTO.Editor;

import com.hav.hav_imobiliaria.Exceptions.EditorExceptions.ValidEditor;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

@ValidEditor
public record EditorPostRequestDTO(
        @NotBlank(message = "Nome não pode estar em branco")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}( [A-Za-zÀ-ÿ]{2,})+$", message = "Nome inválido")
        String name,
        @NotBlank(message = "E-mail não pode estar em branco")
        @Email(message = "E-mail inválido")
        String email,
        @NotBlank(message = "Celular não pode estar em branco")
        @Pattern(regexp = "\\d{11}$", message = "Celular inválido")
        String celphone,
//        @CPF(message = "CPF inválido")
        @NotBlank(message = "CPF não pode estar em branco")
        String cpf,
        @Pattern(regexp = "\\d{10}$",
                message = "Telefone inválido")
        String phoneNumber,
        Boolean archived,
        @Valid
        @NotNull(message = "Endereço inválido")
        AddressPostRequestDTO address
) {
    public EditorPostRequestDTO convertToDTO(Editor editor) {
        return new EditorPostRequestDTO(
                editor.getName(),
                editor.getEmail(),
                editor.getCpf(),
                editor.getCellphone(),
                editor.getPhoneNumber(),
                editor.getArchived(),
                address);
    }
}
