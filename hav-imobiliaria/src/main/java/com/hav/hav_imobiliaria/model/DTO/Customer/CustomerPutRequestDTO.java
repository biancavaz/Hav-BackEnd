package com.hav.hav_imobiliaria.model.DTO.Customer;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPutRequestDTO;
import lombok.Data;

@Data
public class CustomerPutRequestDTO {
    String cpf;

//    @NotBlank(message = "Nome não pode estar em branco")
//    @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}( [A-Za-zÀ-ÿ]{2,})+$", message = "Nome inválido")
    String name;
//    @NotBlank(message = "E-mail não pode estar em branco")
//    @Email(message = "E-mail inválido")
    String email;
//    @NotBlank(message = "Celular não pode estar em branco")
//    @Pattern(regexp = "\\d{11}$", message = "Celular inválido")
    String cellphone;
    String phoneNumber;
//    @Valid
//    @NotNull(message = "Endereço inválido")
    AddressPutRequestDTO address;
    Integer imageId;
}
