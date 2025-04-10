package com.hav.hav_imobiliaria.model.DTO.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressMapGetResponseDTO {
    String neighborhood;
    String city;
}
