package com.hav.hav_imobiliaria.model.DTO.Schedules;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerScheduleGetDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleGetDTO {
    private LocalDate day;
    private LocalTime start_hour;
    private LocalTime end_hour;
    private CustomerScheduleGetDTO customer;

}
