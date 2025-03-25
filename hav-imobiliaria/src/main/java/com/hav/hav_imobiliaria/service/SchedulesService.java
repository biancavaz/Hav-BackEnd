package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleChangeCustomerDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.SchedulesPostDTO;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import com.hav.hav_imobiliaria.repository.RealtorRepository;
import com.hav.hav_imobiliaria.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SchedulesService {

    private final ScheduleRepository repository;
    private final ModelMapper modelMapper;
    private final RealtorRepository realtorRepository;
    private final CustumerRepository custumerRepository;
    public List<Schedules> findAllByRealtorId(Integer id) {
        return repository.findByRealtorId(id);
    }

    public List<Schedules> createSchedules(List<SchedulesPostDTO> schedulesPostDto){
        schedulesPostDto.forEach(schedulesPostDtox -> System.out.println(schedulesPostDtox.toString()));
        List<Schedules> schedulesList = schedulesPostDto.stream().map(
                schedulesPostDtox -> modelMapper.map(schedulesPostDtox, Schedules.class)).toList();

        System.out.println(schedulesList.size());
        for (int i = 0; i < schedulesList.size(); i++) {
            Integer realtorId = schedulesPostDto.get(i).getRealtor_id();
            Optional<Realtor> realtorOptional = realtorRepository.findById(realtorId);

            if (realtorOptional.isPresent()) {
                schedulesList.get(i).setRealtor(realtorOptional.get());
            } else {
                System.err.println("erro n tratado");
            }
        }
        schedulesList.forEach(schedulesPostDtox -> System.out.println(schedulesPostDtox.toString()));

        List<Schedules> schedulesFinal = repository.saveAll(schedulesList);
        return schedulesList;

    }

    public Schedules addCustomerToSchedule(ScheduleChangeCustomerDTO scheduleChangeCustomerDTO) {
        Schedules schedule = repository.findById(scheduleChangeCustomerDTO.getSchedule_id()).get();
        if(schedule.getCustomer() ==null){
            schedule.setCustomer(custumerRepository.findById(scheduleChangeCustomerDTO.getCustomer_id()).get());
        }else{
            System.err.println("erro não tratado de customer ja no schedule");
        }
        return repository.save(schedule);

    }
}
