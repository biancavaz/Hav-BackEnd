package com.hav.hav_imobiliaria.model.DTO.Realtor;

import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RealtorPropertySpecificGetResponseDTO {

    String name;

    String email;
    String creci;
    String phoneNumber;
    String image;


}
