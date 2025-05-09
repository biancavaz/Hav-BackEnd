package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationDTO;
import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationGetResponseDTO;
import com.hav.hav_imobiliaria.WebSocket.Service.NotificationService;
import com.hav.hav_imobiliaria.model.DTO.Users.UserConfigurationDto;
import com.hav.hav_imobiliaria.model.DTO.Users.UserConfigurationDtoEdit;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.*;
import com.hav.hav_imobiliaria.security.configSecurity.TokenProvider;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProprietorRepository proprietorRepository;
    private final RealtorRepository realtorRepository;
    private final AdmRepository admRepository;
    private final EditorRepository editorRepository;
    private final NotificationService notificationService;
    private final TokenProvider tokenProvider;
    private final ModelMapper modelMapper;
    private final ScheduleRepository scheduleRepository;

    public UserConfigurationDto findUserByJwt(String cookie) {
        String email = tokenProvider.getEmailFromToken(cookie);
        User user = userRepository.findByEmail(email).get();

        //make the model mapper ignore the user type


        UserConfigurationDto userConfigurationDto = modelMapper.map(user, UserConfigurationDto.class);


        try {
            userConfigurationDto.setImageId(user.getImageUser().getId());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return userConfigurationDto;
    }


    public void editUserByJwt(String cookie, UserConfigurationDtoEdit userConfigurationDtoEdit) {
        String email = tokenProvider.getEmailFromToken(cookie);
        User user = userRepository.findByEmail(email).get();

        user.setAddress(modelMapper.map(userConfigurationDtoEdit.getAddress(), Address.class));
        user.setName(userConfigurationDtoEdit.getName());
        user.setEmail(userConfigurationDtoEdit.getEmail());
        user.setCellphone(userConfigurationDtoEdit.getCelphone());
        user.setPhoneNumber(userConfigurationDtoEdit.getPhoneNumber());

        userRepository.save(user);


    }

    public Long getAllRegistredNumber() {
        return userRepository.count();
    }

    public Long getPercentageProprietors() {
        Long proprietors = proprietorRepository.count();
        Long users = userRepository.count();
        return proprietors * 100 / users;
    }

    public Long getQuantityClients() {
        Long proprietors = proprietorRepository.count();
        Long realtors = realtorRepository.count();
        Long adms = admRepository.count();
        Long editors = editorRepository.count();
        long notClients = proprietors + realtors + adms + editors;
        long allUsers = userRepository.count();
        long quantityClients = allUsers - notClients;
        return quantityClients;
    }

    public Long getPercentageClients() {
        Long proprietors = proprietorRepository.count();
        Long realtors = realtorRepository.count();
        Long adms = admRepository.count();
        Long editors = editorRepository.count();
        Long notClients = proprietors + realtors + adms + editors;
        long allUsers = userRepository.count();
        long quantityClients = allUsers - notClients;
        return (quantityClients * 100) / allUsers;
    }

    public double getPercentageArchived() {
        List<User> archived = userRepository.findAll();
        if (archived.isEmpty()) {
            return 0.0;
        }

        long archiveStatus = archived
                .stream()
                .filter(User::getArchived)
                .count();
        return (archiveStatus * 100.0) / archived.size();

    }

    public Long getQuantityArchived() {
        return userRepository.findAll()
                .stream()
                .filter(User::getArchived)
                .count();
    }

    public void checkAndNotifyUsersAboutNewProperty(Property newProperty) {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            String preferredType = newProperty.getPreferredPropertyType(user);

            if (preferredType != null && preferredType.equalsIgnoreCase
                    (newProperty.getPropertyType())) {
                NotificationDTO dto = new NotificationDTO();
                dto.setTitle("Nova propriedade do seu interesse!");
                dto.setContent("Uma propriedade semelhante às que você costuma gostar está agora em nosso site.");
                dto.setDataEnvio(LocalDateTime.now());
                dto.setRead(false);
                dto.setIds(List.of(user.getId()));

                NotificationGetResponseDTO not = new NotificationGetResponseDTO(
                        dto.getId(),
                        dto.getTitle(),
                        dto.getContent(),
                        dto.getRead(),
                        dto.getDataEnvio()
                );

                notificationService.salvarNotificacaoDePreferencia(user, not);
            }
        }
    }

}
