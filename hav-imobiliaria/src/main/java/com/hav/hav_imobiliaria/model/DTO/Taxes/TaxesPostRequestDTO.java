<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/dto/Taxes/TaxesPostRequestDTO.java
package com.hav.hav_imobiliaria.model.dto.Taxes;
========
package com.hav.hav_imobiliaria.model.DTO.Taxes;
>>>>>>>> main:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Taxes/TaxesPostRequestDTO.java

import com.hav.hav_imobiliaria.model.entity.Taxes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TaxesPostRequestDTO(
        @PositiveOrZero @NotNull Double condominiumFee,
        @PositiveOrZero @NotNull Double iptu) {

    public Taxes convert() {
        return Taxes.builder()
                .condominiumFee(condominiumFee)
                .iptu(iptu)
                .build();
    }
}
