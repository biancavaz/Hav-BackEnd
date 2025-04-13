package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.Exceptions.RealtorExceptions.ValidRealtor;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

@ValidRealtor
public record RealtorPostRequestDTO(
        @NotBlank(message = "Nome não pode estar em branco")
        @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}( [A-Za-zÀ-ÿ]{2,})+$", message = "Nome inválido")
        String name,
        @NotBlank(message = "E-mail não pode estar em branco")
        @Email(message = "E-mail inválido")
        String email,
//        @CPF(message = "CPF inválido")
        @NotBlank(message = "CPF não pode estar em branco")
        String cpf,
        @NotNull(message = "Celular inválido")
        @Pattern(regexp = "\\d{11}$", message = "Celular inválido")
        String celphone,
        @NotBlank(message = "CRECI não pode estar em branco")
        String creci,
        @Pattern(regexp = "\\d{10}$",
                message = "Telefone inválido")
        String phoneNumber,
        boolean archived,
        @Valid AddressPostRequestDTO address
) {
    public RealtorPostRequestDTO convertToDTO(Realtor realtor) {
        return new RealtorPostRequestDTO(
                realtor.getName(),
                realtor.getEmail(),
                realtor.getCpf(),
                realtor.getCellphone(),
                realtor.getCreci(),
                realtor.getPhoneNumber(),
                realtor.getArchived(),
                address);
    }
}
