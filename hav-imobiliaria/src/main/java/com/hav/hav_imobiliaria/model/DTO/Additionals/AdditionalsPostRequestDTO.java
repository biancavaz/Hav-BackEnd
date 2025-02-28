<<<<<<<< HEAD:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/dto/Additionals/AdditionalsPostRequestDTO.java
package com.hav.hav_imobiliaria.model.dto.Additionals;
========
package com.hav.hav_imobiliaria.model.DTO.Additionals;
>>>>>>>> main:hav-imobiliaria/src/main/java/com/hav/hav_imobiliaria/model/DTO/Additionals/AdditionalsPostRequestDTO.java

import com.hav.hav_imobiliaria.model.entity.Additionals;
import jakarta.validation.constraints.NotBlank;

public record AdditionalsPostRequestDTO(
        @NotBlank String name
) {
    public Additionals convert() {
        return Additionals.builder().name(name).build();
    }
}
