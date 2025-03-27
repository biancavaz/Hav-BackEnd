package com.hav.hav_imobiliaria.model.DTO.Schedules;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyScheduleGetDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleGetDTO {

    private Integer id;
    private LocalDate day;
    private LocalTime start_hour;
    private CustomerScheduleGetDTO customer;
    private PropertyScheduleGetDTO property;

}
