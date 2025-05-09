package com.hav.hav_imobiliaria.model.DTO.Address;

import com.hav.hav_imobiliaria.model.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCardGetResponseDTO {

    String neighborhood;
    String city;
    String street;
    Integer propertyNumber;

    public AddressCardGetResponseDTO(Address address) {
        this.neighborhood = address.getNeighborhood();
        this.city = address.getCity();
        this.street = address.getStreet();
        this.propertyNumber = address.getPropertyNumber();

    }
}
