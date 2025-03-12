package com.hav.hav_imobiliaria.model.DTO.Proprietor;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

public record ProprietorPostDTO(
        @NotBlank(message = "Nome não pode estar em branco")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}( [A-Za-zÀ-ÿ]{2,})+$", message = "Nome inválido")
        String name,
        @NotBlank(message = "E-mail não pode estar em branco")
        @Email(message = "E-mail inválido")
        String email,
        @NotNull(message = "Celular inválido")
        @Pattern(regexp = "^\\+55\\d{11}$", message = "Celular inválido")
        String celphone,
        @CPF(message = "CPF inválido")
        String cpf,
        @CNPJ(message = "CNPJ inválido")
        String cnpj,
        @Pattern(regexp = "^\\+55\\d{10}$",
                message = "Telefone inválido")
        String phoneNumber,
        Boolean archived,
        @Valid AddressPostRequestDTO address
) {
    public ProprietorPostDTO convertToDTO(Proprietor proprietor) {
        return new ProprietorPostDTO(
                proprietor.getName(),
                proprietor.getEmail(),
                proprietor.getCelphone(),
                proprietor.getCpf(),
                proprietor.getCnpj(),
                proprietor.getPhoneNumber(),
                proprietor.getArchived(),
                address);
    }
}
