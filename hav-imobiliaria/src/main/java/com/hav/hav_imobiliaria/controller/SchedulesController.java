package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTOwithId;
import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleChangeCustomerDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.SchedulesPostDTO;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.service.SchedulesService;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/schedules")
public class SchedulesController {

    private SchedulesService service;
    private ModelMapper modelMapper;
    @GetMapping
    @RequestMapping("/customer")
    public List<ScheduleGetDTO> getSchedulesByCustomer(@CookieValue("token") String token){
        return service.findAllSchedulesCustomer(token);
    }
    @GetMapping
    @RequestMapping
    public List<ScheduleGetDTO> getSchedulesByRealtor(@CookieValue("token") String token){
        return service.findAllByRealtorIdAndFuture(token).stream().map(x -> modelMapper.map(x, ScheduleGetDTO.class)).collect(Collectors.toList());
    }
    @GetMapping
    @RequestMapping("/free/{id}")
    public List<ScheduleGetDTO> getSchedulesByRealtorFree(@PathVariable Integer id){
        return service.findAllByRealtorIdAndFutureFree(id).stream().map(x -> modelMapper.map(x, ScheduleGetDTO.class)).collect(Collectors.toList());
    }
    @GetMapping("/hourAndDay/{day}/{start_hour}/{propertyId}")
    public List<RealtorGetResponseDTOwithId> findRealtorsForHourAndDay(
            @PathVariable LocalDate day,
            @PathVariable LocalTime start_hour,
            @PathVariable Integer propertyId) {
        return service.findRealtorsForHourAndDay(day, start_hour, propertyId);
    }
    @GetMapping
    @RequestMapping("/free/property/{id}")
    public List<ScheduleGetDTO> getSchedulesByPropertyRealtorsFree(@PathVariable Integer id){
        return service.findAllByPropertyIdAndFutureFree(id).stream().map(x -> modelMapper.map(x, ScheduleGetDTO.class)).collect(Collectors.toList());
    }

    @PostMapping
    public List<Schedules> createSchedules( @CookieValue("token") String token, @RequestBody List<SchedulesPostDTO> schedulesPostDto){
        return service.createSchedules(schedulesPostDto, token);
    }
    @DeleteMapping
    public String deleteSchedules(@RequestBody List<Integer> idList){
        service.deleteSchedules(idList);
        return "deletado";
    }

    @PutMapping("/presence")
    public List<ScheduleGetDTO> addCustomerToSchedule(@RequestBody ScheduleChangeCustomerDTO scheduleChangeCustomerDTO, @CookieValue("token") String token){
        return service.addCustomerToSchedule(scheduleChangeCustomerDTO, token);
    }
    @GetMapping("/history/realtor")
    public Page<ScheduleGetDTO> getRealtorSchedulesHistory(@CookieValue("token") String token,
                @Nullable @RequestParam LocalDate latestDate,
                @Nullable @RequestParam String status,
            @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10);
        return service.findRealtorScheduleHistory(token, latestDate, status, pageable);
    }

    @GetMapping("/history/customer")
    public Page<ScheduleGetDTO> getCustomerSchedulesHistory(@CookieValue("token") String token,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @Nullable @RequestParam LocalDate latestDate,
                                                            @Nullable @RequestParam String status) {
        
        Pageable pageable = PageRequest.of(page, 10);
        return service.findCustomerScheduleHistory(token, latestDate, status, pageable);
    }
    @PatchMapping("/changeStatus/{id}/{status}")
    public void changeStatus(@PathVariable Integer id, @PathVariable String status){
        service.alterStatus(id, status);
    }

}
