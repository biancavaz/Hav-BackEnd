package com.hav.hav_imobiliaria.model.DTO.Schedules;

import lombok.Data;

@Data
public class ScheduleChangeCustomerDTO {
    private Integer customer_id;
    private Integer property_id;
    private Integer schedule_id;

}
