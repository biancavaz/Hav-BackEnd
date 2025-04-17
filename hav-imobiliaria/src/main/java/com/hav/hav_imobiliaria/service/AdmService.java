package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Adm.AdmFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.model.entity.Users.User;
//import com.hav.hav_imobiliaria.model.entity.Users.UsersDetails;
import com.hav.hav_imobiliaria.repository.AdmRepository;
import com.hav.hav_imobiliaria.security.modelSecurity.Role;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.UserRepositorySecurity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AdmService {

    private final AdmRepository repository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;
    private final PasswordGeneratorService passwordGeneratorService;
    private final EmailSenderService emailSenderService;
    private final UserRepositorySecurity userRepositorySecurity;
    private final PasswordEncoder passwordEncoder;

    public AdmPostRequestDTO createAdm(
            @Valid AdmPostRequestDTO admPostDTO,
            MultipartFile image) {

        Adm adm = modelMapper.map(admPostDTO, Adm.class);

        String password = passwordGeneratorService.generateSecurePassword();






        UserSecurity newUserSec = new UserSecurity();

        newUserSec.setEmail(adm.getEmail());
        newUserSec.setPassword(passwordEncoder.encode(password));
        newUserSec.setName(adm.getName());
        newUserSec.setRole(Role.valueOf("ADMIN"));

        userRepositorySecurity.save(newUserSec);

        adm.setUserSecurity(newUserSec);

        Adm savedAdm = repository.save(adm);

        emailSenderService.sendPasswordNewAccount(adm.getEmail(), password, "Administrador");

        if (image != null) {
            imageService.uploadUserImage(savedAdm.getId(), image);
        }
        return admPostDTO.convertToDTO(adm);
    }

    public Adm updateAdm(
            @Positive @NotNull Integer id,
            @Valid AdmPutRequestDTO admPutDTO,
            @Positive Integer deletedImageId,
            MultipartFile newImage) {

        Adm adm = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Editor com o ID " + id + " não encontrado."));

        modelMapper.map(admPutDTO, adm);
        System.out.println(adm.toString());
        if (deletedImageId != null) {
            imageService.deleteUserImage(deletedImageId);
        }

        if (newImage != null) {
            imageService.uploadUserImage(id, newImage);
        }

        return repository.save(adm);
    }

    public Page<AdmListGetResponseDTO> findAllByFilter(Pageable pageable, AdmFilterPostResponseDTO admDto) {

        Adm adm = modelMapper.map(admDto, Adm.class);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Adm> example = Example.of(adm, matcher);

        Page<Adm> admList = repository.findAll(example, pageable);


        Page<AdmListGetResponseDTO> admListGetResponseDtos = admList.map(admx ->
                modelMapper.map(admx, AdmListGetResponseDTO.class)
        );

        return admListGetResponseDtos;
    }

    @Transactional
    public void removeList(List<Integer> idList) {
        List<Adm> admList = repository.findAllById(idList);
        repository.deleteByIdIn(idList);
        for(Adm adm : admList){
            UserSecurity userSecurity = userRepositorySecurity.findUserSecurityByEmail(adm.getEmail());
            userRepositorySecurity.delete(userSecurity);
        }
    }

    public void changeArchiveStatus(List<Integer> admIds) {
        List<Adm> admins = repository.findAllById(admIds);
        admins.forEach(User::changeArchiveStatus);
        repository.saveAll(admins);
    }

    public AdmPutRequestDTO findAdmById(Integer id) {
        Adm adm = repository.findById(id).get();
        AdmPutRequestDTO admPutRequestDTO = modelMapper.map(adm, AdmPutRequestDTO.class);

        // Converte a entidade adm para o DTO
        return admPutRequestDTO;
    }
}
