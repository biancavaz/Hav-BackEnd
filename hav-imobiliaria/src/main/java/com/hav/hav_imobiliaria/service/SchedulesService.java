package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTOwithId;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleChangeCustomerDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.ScheduleGetDTO;
import com.hav.hav_imobiliaria.model.DTO.Schedules.SchedulesPostDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.ImageProperty;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.*;
import com.hav.hav_imobiliaria.security.configSecurity.TokenProvider;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchedulesService {

    private final ScheduleRepository repository;
    private final ModelMapper modelMapper;
    private final RealtorRepository realtorRepository;
    private final CustumerRepository custumerRepository;
    private final PropertyRepository propertyRepository;
    private final TokenProvider tokenProvider;
    private final ImageService imageService;
    private final UserRepository userRepository;

    public List<ScheduleGetDTO> findAllByRealtorIdAndFuture(String token) {
        String realtorEmail = tokenProvider.getEmailFromToken(token);
        Realtor realtor = realtorRepository.findByEmail(realtorEmail);

        List<Schedules> sortedList = sortSchedulesDayAndHour(repository.findByRealtorIdAndDayGreaterThanEqual(realtor.getId(), LocalDate.now()));
        List<ScheduleGetDTO> list = sortedList.stream().map(x -> modelMapper.map(x, ScheduleGetDTO.class)).collect(Collectors.toList());
        try{
            for(int i=0; i<sortedList.size(); i++){
                if(sortedList.get(i).getCustomer() !=null){
                    if(sortedList.get(i).getRealtor().getImageUser() !=null){
                        System.out.println("entrou");
                        String mainImage = Base64.getEncoder().encodeToString(imageService.getUserImage(sortedList.get(i).getRealtor().getImageUser().getId()));
                        System.out.println(mainImage);
                        list.get(i).getRealtor().setMainImageRealtor(mainImage);
                    }
                    if(sortedList.get(i).getCustomer().getImageUser() !=null){
                        String mainImage = Base64.getEncoder().encodeToString(imageService.getUserImage(sortedList.get(i).getCustomer().getImageUser().getId()));

                        list.get(i).getCustomer().setMainImageCustomer(mainImage);
                    }

                    for(ImageProperty image: sortedList.get(i).getProperty().getImageProperties()){
                        if(image.getMainImage()){
                            String mainImage = Base64.getEncoder().encodeToString(imageService.getMainPropertyImage(image.getId()));
                            list.get(i).getProperty().setMainImageProperty(mainImage);
                        }
                    }
                }


            }
        }catch (Exception e){
            System.err.println(e);
        }
        
        return list;
    }
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

    public List<Schedules> createSchedules(List<SchedulesPostDTO> schedulesPostDto, String token){

        List<Schedules> schedulesList = schedulesPostDto.stream().map(
                schedulesPostDtox -> modelMapper.map(schedulesPostDtox, Schedules.class)).toList();

        for (int i = 0; i < schedulesList.size(); i++) {
            String realtorEmail = tokenProvider.getEmailFromToken(token);

            Realtor realtor = realtorRepository.findByEmail(realtorEmail);

            if (realtor != null) {
                schedulesList.get(i).setRealtor(realtor);
            } else {
                throw new RuntimeException("Realtor not found");
            }
        }

        repository.saveAll(schedulesList);
        return schedulesList;

    }

    public List<ScheduleGetDTO> addCustomerToSchedule(ScheduleChangeCustomerDTO scheduleChangeCustomerDTO, String token) {
        String customerEmail = tokenProvider.getEmailFromToken(token);
        Customer customer = custumerRepository.findByEmail(customerEmail);
        if(customer == null){
            throw new RuntimeException("Customer not found");
        }
        List<Schedules> schedules = repository.findAllById(scheduleChangeCustomerDTO.getSchedule_id());
        for(int i=0; i<schedules.size(); i++){
            schedules.get(i).setStatus(scheduleChangeCustomerDTO.getStatus());
            if(schedules.get(i).getCustomer() ==null){
                schedules.get(i).setCustomer(custumerRepository.findById(customer.getId()).get());
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

    public Page<ScheduleGetDTO> findRealtorScheduleHistory(String token, LocalDate latestDate, String status, Pageable pageable) {
        String realtorEmail = tokenProvider.getEmailFromToken(token);
        Realtor realtor = realtorRepository.findByEmail(realtorEmail);
        Page<Schedules> schedules = repository.findByRealtorIdAndDayLessThanAndCustomerIsNotNullAndPropertyIsNotNullAndStatusEquals(
                realtor.getId(), LocalDate.now(), latestDate, status, pageable);
        return schedules.map(schedule -> modelMapper.map(schedule, ScheduleGetDTO.class));
    }
    public Page<ScheduleGetDTO> findCustomerScheduleHistory(String token, LocalDate latestDate, String status, Pageable pageable) {
        String customerEmail = tokenProvider.getEmailFromToken(token);
        Customer customer = custumerRepository.findByEmail(customerEmail);
        Page<Schedules> schedules = repository.findByCustomerIdAndDayLessThanAndCustomerIsNotNullAndPropertyIsNotNullAndStatusEquals(
                customer.getId(), LocalDate.now(), latestDate, status, pageable);

        return schedules.map(schedule -> modelMapper.map(schedule, ScheduleGetDTO.class));
    }

    public void alterStatus(Integer id, String status) {
        Schedules schedules = repository.findById(id).get();
        schedules.setStatus(status);
        repository.save(schedules);
    }
    public List<ScheduleGetDTO> findAllSchedulesCustomer(String token) {
        String customerEmail = tokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(customerEmail).get();

        List<Schedules> sortedList = sortSchedulesDayAndHour(repository.findByCustomerIdAndDayGreaterThanEqual(user.getId(), LocalDate.now()));
        List<ScheduleGetDTO> list = sortedList.stream().map(x -> modelMapper.map(x, ScheduleGetDTO.class)).collect(Collectors.toList());
        try{
            for(int i=0; i<sortedList.size(); i++){
                if(sortedList.get(i).getCustomer() !=null){
                    if(sortedList.get(i).getRealtor().getImageUser() !=null){
                        System.out.println("entrou");
                        String mainImage = Base64.getEncoder().encodeToString(imageService.getUserImage(sortedList.get(i).getRealtor().getImageUser().getId()));
                        System.out.println(mainImage);
                        list.get(i).getRealtor().setMainImageRealtor(mainImage);
                    }
                    if(sortedList.get(i).getCustomer().getImageUser() !=null){
                        String mainImage = Base64.getEncoder().encodeToString(imageService.getUserImage(sortedList.get(i).getCustomer().getImageUser().getId()));

                        list.get(i).getCustomer().setMainImageCustomer(mainImage);
                    }

                    for(ImageProperty image: sortedList.get(i).getProperty().getImageProperties()){
                        if(image.getMainImage()){
                            String mainImage = Base64.getEncoder().encodeToString(imageService.getMainPropertyImage(image.getId()));
                            list.get(i).getProperty().setMainImageProperty(mainImage);
                        }
                    }
                }


            }
        }catch (Exception e){
            System.err.println(e);
        }

        return list;
    }
    
}
