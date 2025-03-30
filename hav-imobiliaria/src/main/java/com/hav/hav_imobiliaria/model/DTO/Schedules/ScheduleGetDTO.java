package com.hav.hav_imobiliaria.model.DTO.Schedules;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorScheduleGetDTO;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleGetDTO {

    private Integer id;
    private LocalDate day;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Time must be in HH:mm format")
    private LocalTime start_hour;
    private CustomerScheduleGetDTO customer;
    private PropertyScheduleGetDTO property;
    private RealtorScheduleGetDTO realtor;

}
