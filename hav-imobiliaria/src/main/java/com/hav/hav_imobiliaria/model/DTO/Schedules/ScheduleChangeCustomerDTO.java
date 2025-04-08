package com.hav.hav_imobiliaria.model.DTO.Schedules;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleChangeCustomerDTO {
    private Integer customer_id;
    private Integer property_id;
    private List<Integer> schedule_id;

    public List<Integer> getSchedule_id() {
        return schedule_id;
    }
}
