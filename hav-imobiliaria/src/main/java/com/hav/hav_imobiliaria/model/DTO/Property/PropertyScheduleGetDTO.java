package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressGetScheduleDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PropertyScheduleGetDTO {
    String propertyCode;
    String purpose;
    AddressGetScheduleDTO address;

}
