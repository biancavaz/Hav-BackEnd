package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleChangeCustomerDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.SchedulesPostDTO;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.service.SchedulesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/schedules")
public class SchedulesController {

    private SchedulesService service;

    @GetMapping
    @RequestMapping("{id}")
    public List<ScheduleGetDTO> getSchedulesByRealtor(@PathVariable Integer id){
        return service.findAllByRealtorIdAndFuture(id);
    }

    @PostMapping
    public List<Schedules> createSchedules(@RequestBody List<SchedulesPostDTO> schedulesPostDto){
        return service.createSchedules(schedulesPostDto);
    }
    @DeleteMapping
    public String deleteSchedules(@RequestBody List<Integer> idList){
        service.deleteSchedules(idList);
        return "deletado";
    }

    @PostMapping("/presence")
    public Schedules addCustomerToSchedule(@RequestBody ScheduleChangeCustomerDTO scheduleChangeCustomerDTO){
        return service.addCustomerToSchedule(scheduleChangeCustomerDTO);
    }
}
