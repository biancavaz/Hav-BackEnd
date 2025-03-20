package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Adm.AdmFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.AdmRepository;
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

    public AdmPostRequestDTO createAdm(
            @Valid AdmPostRequestDTO admPostDTO,
            MultipartFile image) {

        Adm adm = modelMapper.map(admPostDTO, Adm.class);

        Adm savedadm = repository.save(adm);

        if (image != null) {
            imageService.uploadImages(savedadm.getId(), image);
        }

        return admPostDTO.convertToDTO(adm);
    }

    public Adm editAdm(
            @Positive @NotNull Integer id,
            @Valid AdmPutRequestDTO admPutDTO) {

        Adm existingAdm = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Editor com o ID " + id + " não encontrado."));

        // Atualiza apenas os campos que vieram no DTO (mantendo os valores existentes)
        modelMapper.map(admPutDTO, existingAdm);

        return repository.save(existingAdm);
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
        repository.deleteByIdIn(idList);

    }

    public void changeArchiveStatus(List<Integer> admIds) {
        List<Adm> admins = repository.findAllById(admIds);
        admins.forEach(User::changeArchiveStatus);
        repository.saveAll(admins);
    }
}
