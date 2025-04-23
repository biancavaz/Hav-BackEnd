package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationDTO;
import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationGetResponseDTO;
import com.hav.hav_imobiliaria.WebSocket.Service.NotificationService;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTOwithId;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleChangeCustomerDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.SchedulesPostDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import com.hav.hav_imobiliaria.repository.RealtorRepository;
import com.hav.hav_imobiliaria.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchedulesService {

    private final ScheduleRepository repository;
    private final ModelMapper modelMapper;
    private final RealtorRepository realtorRepository;
    private final CustumerRepository custumerRepository;
    private final PropertyRepository propertyRepository;
    private final NotificationService notificationService;

    public List<Schedules> findAllByRealtorIdAndFuture(Integer id) {
        List<Schedules> sortedList = sortSchedulesDayAndHour(repository.findByRealtorIdAndDayGreaterThanEqual(id, LocalDate.now()));

        return sortedList;
    }
    public List<Schedules> findAllByRealtorIdAndFutureFree(Integer id) {

        List<Schedules> schedules = repository.findByRealtorIdAndDayGreaterThanEqual(id, LocalDate.now());
        List<Schedules> schedulesFree = schedules.stream().filter(sch -> sch.getCustomer() == null && sch.getProperty() == null).toList();
        List<Schedules> sortedList = sortSchedulesDayAndHour(schedulesFree);

        return sortedList;
    }
    public List<Schedules> findAllByPropertyIdAndFutureFree(Integer id) {
        Property property = propertyRepository.findById(id).get();
        List<Schedules> schedules = new ArrayList<>();

        for (int i = 0; i < property.getRealtors().size(); i++) {
            schedules.addAll(repository.findByRealtorIdAndDayGreaterThanEqual(
                    property.getRealtors().get(i).getId(), LocalDate.now()
            ));
        }

        List<Schedules> schedulesFree = schedules.stream().filter(sch -> sch.getCustomer() == null && sch.getProperty() == null).toList();
        schedulesFree = schedulesFree
                .stream()
                .collect(Collectors.toMap(
                        s -> s.getDay().toString() + s.getStart_hour().toString(),
                        s -> s,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .toList();
        List<Schedules> sortedList = sortSchedulesDayAndHour(schedulesFree);



        return sortedList;
    }

    public List<Schedules> createSchedules(List<SchedulesPostDTO> schedulesPostDto){
        schedulesPostDto.forEach(dto -> System.out.println(dto.toString()));

        List<Schedules> schedulesList = schedulesPostDto.stream()
                .map(dto -> modelMapper.map(dto, Schedules.class))
                .toList();

        for (int i = 0; i < schedulesList.size(); i++) {
            Integer realtorId = schedulesPostDto.get(i).getRealtor_id();
            Optional<Realtor> realtorOptional = realtorRepository.findById(realtorId);

            if (realtorOptional.isPresent()) {
                schedulesList.get(i).setRealtor(realtorOptional.get());

                // Enviar notificação para o corretor (associado ao agendamento)
                Realtor realtor = realtorRepository.findById(realtorId)
                        .orElseThrow(() -> new RuntimeException("Realtor não encontrado com id: " + realtorId));

                NotificationDTO notificationDTO = new NotificationDTO();
                notificationDTO.setTitle("Novo agendamento");
                notificationDTO.setContent("Você foi selecionado para um agendamento");
                notificationDTO.setDataEnvio(LocalDateTime.now());
                notificationDTO.setRead(false);

                NotificationGetResponseDTO notification = new NotificationGetResponseDTO();
                notification.setTitle(notificationDTO.getTitle());
                notification.setContent(notificationDTO.getContent());
                notification.setRead(false);
                notification.setDataEnvio(LocalDateTime.now());

                realtorRepository.save(realtor);
                notificationDTO.setIds(List.of(realtorId));

                NotificationGetResponseDTO notDTO = new NotificationGetResponseDTO(
                        notification.getId(),
                        notification.getTitle(),
                        notification.getContent(),
                        notification.getRead(),
                        notification.getDataEnvio()
                );

                notificationService.enviarNotificacao(notDTO, List.of(realtor.getId()));
            } else {
                System.err.println("Realtor não encontrado: id=" + schedulesPostDto.get(i).getRealtor_id());
            }
        }

        return repository.saveAll(schedulesList);
    }

    public List<ScheduleGetDTO> addCustomerToSchedule(ScheduleChangeCustomerDTO scheduleChangeCustomerDTO) {
        List<Schedules> schedules = repository.findAllById(scheduleChangeCustomerDTO.getSchedule_id());
        for(int i=0; i<schedules.size(); i++){
            schedules.get(i).setStatus(scheduleChangeCustomerDTO.getStatus());
            if(schedules.get(i).getCustomer() ==null){
                schedules.get(i).setCustomer(custumerRepository.findById(scheduleChangeCustomerDTO.getCustomer_id()).get());
            }else{
                System.err.println("erro não tratado de customer ja no schedule");
            }
            if(schedules.get(i).getProperty() ==null){
                schedules.get(i).setProperty(propertyRepository.findById(scheduleChangeCustomerDTO.getProperty_id()).get());
            }else{
                System.err.println("erro não tratado de customer ja no schedule");
            }
        }

        List<Schedules> savedSchedules = repository.saveAll(schedules);

        return modelMapper.map(savedSchedules, new TypeToken<List<ScheduleGetDTO>>() {}.getType());

    }

    public void deleteSchedules(List<Integer> idList) {
        repository.deleteAllById(idList);
    }

    public List<Schedules> sortSchedulesDayAndHour(List<Schedules> list){
        List<Schedules> muttableList = new ArrayList<>(list); // Make it mutable
        if(list.size()>0){

            muttableList.sort(Comparator.comparing(Schedules::getDay));
            for(int i=0; i<muttableList.size(); i++){
                for(int y=0; y<muttableList.size(); y++){
                    if(muttableList.get(i).getDay().equals(muttableList.get(y).getDay())){
                        if(muttableList.get(i).getStart_hour().isAfter(muttableList.get(y).getStart_hour()) && y>i){
                            Schedules momentSchedule = muttableList.get(i);
                            muttableList.set(i, muttableList.get(y));
                            muttableList.set(y, momentSchedule);
                        }
                    }

                }
            }
        }

        return muttableList;
    }

    public List<RealtorGetResponseDTOwithId> findRealtorsForHourAndDay(LocalDate day, LocalTime startHour, Integer propertyId) {
        Property property = propertyRepository.findById(propertyId).get();
        List<Schedules> schedules = repository.findByDayAndStartHour(day, startHour);
        List<Schedules> schedulesFree = schedules.stream().filter(sch -> sch.getCustomer() == null && sch.getProperty() == null).toList();

        List<Realtor> realtors = schedulesFree.stream()
                .map(Schedules::getRealtor)
                .filter(property.getRealtors()::contains)
                .distinct() // Remove duplicatas
                .collect(Collectors.toList());

        return realtors.stream()
                .map(realtor -> modelMapper.map(realtor, RealtorGetResponseDTOwithId.class))
                .collect(Collectors.toList());
    }

    public Page<ScheduleGetDTO> findRealtorScheduleHistory(Integer id, Pageable pageable) {
        Page<Schedules> schedules = repository.findByRealtorIdAndDayLessThanAndCustomerIsNotNullAndPropertyIsNotNull(
                id, LocalDate.now(), pageable);
        return schedules.map(schedule -> modelMapper.map(schedule, ScheduleGetDTO.class));
    }
    public Page<ScheduleGetDTO> findCustomerScheduleHistory(Integer id, LocalDate latestDate, String status, Pageable pageable) {
        Page<Schedules> schedules = repository.findByCustomerIdAndDayLessThanAndCustomerIsNotNullAndPropertyIsNotNullAndStatusEquals(
                id, LocalDate.now(), latestDate, status, pageable);

        return schedules.map(schedule -> modelMapper.map(schedule, ScheduleGetDTO.class));
    }

    public void alterStatus(Integer id, String status) {
        Schedules schedules = repository.findById(id).get();
        schedules.setStatus(status);
        repository.save(schedules);
    }

    public void notifyNewSchedule(Schedules schedule){

    }
}
