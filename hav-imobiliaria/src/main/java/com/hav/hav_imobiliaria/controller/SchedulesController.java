package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleChangeCustomerDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.SchedulesPostDTO;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.service.SchedulesService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/schedules")
public class SchedulesController {

    private SchedulesService service;
    private ModelMapper modelMapper;
    @GetMapping
    @RequestMapping("{id}")
    public List<ScheduleGetDTO> getSchedulesByRealtor(@PathVariable Integer id){
        return service.findAllByRealtorIdAndFuture(id).stream().map(x -> modelMapper.map(x, ScheduleGetDTO.class)).collect(Collectors.toList());
    }
    @GetMapping
    @RequestMapping("/free/{id}")
    public List<ScheduleGetDTO> getSchedulesByRealtorFree(@PathVariable Integer id){
        return service.findAllByRealtorIdAndFutureFree(id).stream().map(x -> modelMapper.map(x, ScheduleGetDTO.class)).collect(Collectors.toList());
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
