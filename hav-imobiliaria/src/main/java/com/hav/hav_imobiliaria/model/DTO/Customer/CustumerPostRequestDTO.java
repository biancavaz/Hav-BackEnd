package com.hav.hav_imobiliaria.model.DTO.Customer;

import com.hav.hav_imobiliaria.Exceptions.CustomerExceptions.ValidCustomer;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

@ValidCustomer
public record CustumerPostRequestDTO(
        @NotBlank(message = "Nome não pode estar em branco")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}( [A-Za-zÀ-ÿ]{2,})+$", message = "Nome inválido")
        String name,
        @Email(message = "E-mail inválido")
        @NotBlank(message = "E-mail não pode estar em branco")
        String email,
        @Pattern(regexp = "\\d{11}$", message = "Celular inválido")
        String celphone,
        @CPF(message = "CPF inválido")
        @NotBlank(message = "CPF não pode estar em branco")
        String cpf,
        @Pattern(regexp = "\\d{10}$", message = "Telefone inválido")
        String phoneNumber,
        Boolean archived,
        @Valid @NotNull(message = "Endereço inválido") AddressPostRequestDTO address
) {
    public CustumerPostRequestDTO convertToDTO(Customer customer) {
        return new CustumerPostRequestDTO(
                customer.getName(),
                customer.getEmail(),
                customer.getCelphone(),
                customer.getCpf(),
                customer.getPhoneNumber(),
                customer.getArchived(),
                address);
    }
}
