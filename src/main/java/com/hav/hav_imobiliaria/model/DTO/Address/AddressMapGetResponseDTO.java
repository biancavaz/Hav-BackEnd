package com.hav.hav_imobiliaria.model.DTO.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressMapGetResponseDTO {
    Integer propertyNumber;
    String state;

    String street;
    String neighborhood;
    String city;
}
