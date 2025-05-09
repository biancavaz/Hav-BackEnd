package com.hav.hav_imobiliaria.model.DTO.Property;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressGetScheduleDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PropertyScheduleGetDTO {
    Integer id;
    String propertyCode;
    String price;
    String purpose;
    String propertyType;
    String mainImageProperty;
    AddressGetScheduleDTO address;

}
