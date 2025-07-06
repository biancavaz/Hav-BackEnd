package com.hav.hav_imobiliaria.model.DTO.Address;

import lombok.Data;

@Data
public class AddressGetScheduleDTO {
    String cep;
    String street;
    String neighborhood;
    String city;
    String state;
    Integer propertyNumber;
    String complement;
}
