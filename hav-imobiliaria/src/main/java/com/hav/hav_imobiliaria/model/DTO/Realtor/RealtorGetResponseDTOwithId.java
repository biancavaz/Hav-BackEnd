package com.hav.hav_imobiliaria.model.DTO.Realtor;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RealtorGetResponseDTOwithId {
    Integer id;
    String cpf;
    String name;
    String email;
    String celphone;
    String creci;

}
