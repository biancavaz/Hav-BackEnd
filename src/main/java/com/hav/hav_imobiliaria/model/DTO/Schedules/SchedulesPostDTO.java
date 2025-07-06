package com.hav.hav_imobiliaria.model.DTO.Schedules;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class SchedulesPostDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate day;

    private LocalTime start_hour;




    @Override
    public String toString() {
        return "SchedulesPostDTO{" +
                "day=" + day +
                ", start_hour=" + start_hour +
                '}';
    }
}
