<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/dto/Address/AddressPostRequestDTO.java
package com.hav.hav_imobiliaria.model.dto.Address;
========
package com.hav.hav_imobiliaria.model.DTO.Address;
>>>>>>>> main:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Address/AddressPostRequestDTO.java

import com.hav.hav_imobiliaria.model.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddressPostRequestDTO(
        @NotBlank String cep,
        @NotBlank String street,
        @NotBlank String neighborhood,
        @NotBlank String city,
        @NotBlank String state,
        @Positive @NotNull Integer propertyNumber,
        String complement
) {
    public Address convert() {
        return Address.builder().cep(cep).street(street).
                neighborhood(neighborhood).city(city).state(state).
                propertyNumber(propertyNumber).complement(complement).build();
    }
}
