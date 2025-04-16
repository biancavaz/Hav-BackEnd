package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPostDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.ProprietorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProprietorService {

    private final ProprietorRepository repository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;
    private final PasswordGeneratorService passwordGeneratorService;

    public ProprietorPostDTO createProprietor(
            @Valid ProprietorPostDTO proprietorDTO,
            MultipartFile image) {

        Proprietor proprietor = modelMapper.map(proprietorDTO, Proprietor.class);
        System.out.println(proprietor.getCnpj());


        Proprietor savedProprietor = repository.save(proprietor);

        if (image != null) {
            imageService.uploadUserImage(savedProprietor.getId(), image);
        }

        return proprietorDTO.convertToDTO(savedProprietor);
    }

    public Proprietor updateProprietor(
            @Positive @NotNull Integer id,
            @Valid ProprietorPutRequestDTO proprietorPutDTO,
            @Positive Integer deletedImageId,
            MultipartFile newImage) {

        Proprietor proprietor = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Proprietario com o ID " + id + " não encontrado."));

        modelMapper.map(proprietorPutDTO, proprietor);

        if (deletedImageId != null) {
            imageService.deleteUserImage(deletedImageId);
        }

        if (newImage != null) {
            imageService.uploadUserImage(id, newImage);
        }

        return repository.save(proprietor);
    }

    public Page<ProprietorListGetResponseDTO> findAllByFilter(Pageable pageable, ProprietorFilterPostResponseDTO proprietorDto) {
        Proprietor proprietor = modelMapper.map(proprietorDto, Proprietor.class);


        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Proprietor> example = Example.of(proprietor, matcher);

        Page<Proprietor> proprietorList = repository.findAll(example, pageable);

        modelMapper.typeMap(Proprietor.class, ProprietorListGetResponseDTO.class).addMappings(mapper -> {
            mapper.skip(ProprietorListGetResponseDTO::setPurpose);
            mapper.skip(ProprietorListGetResponseDTO::setNumberOfProperty);
        });

        Page<ProprietorListGetResponseDTO> proprietorListGetResponseDtos = proprietorList.map(proprietorx ->
                modelMapper.map(proprietorx, ProprietorListGetResponseDTO.class)

        );


        for (int i = 0; i < proprietorList.getContent().size(); i++) {


            if (proprietorList.getContent().get(i).getCpf() == null) {
                proprietorListGetResponseDtos.getContent().get(i).setDocument(proprietorList.getContent().get(i).getCnpj());
            } else {
                proprietorListGetResponseDtos.getContent().get(i).setDocument(proprietorList.getContent().get(i).getCpf());
            }

            proprietorListGetResponseDtos.getContent().get(i)
                    .setNumberOfProperty(proprietorList.getContent()
                            .get(i).numberOfProperty());
            proprietorListGetResponseDtos.getContent().get(i)
                    .setPurpose(proprietorList.getContent()
                            .get(i).getPurpose());
        }

        if (proprietorDto.getNumberOfProperty() != null) {
            List<ProprietorListGetResponseDTO> filteredPage = proprietorListGetResponseDtos
                    .map(proprietorx -> modelMapper.map(proprietorx, ProprietorListGetResponseDTO.class))
                    .filter(dto -> dto.getNumberOfProperty() == proprietorDto.getNumberOfProperty())  // Filter where numberOfProperties == 4
                    .stream().collect(Collectors.toList());

            proprietorListGetResponseDtos = new PageImpl<>(filteredPage, pageable, filteredPage.size());

        }

        return proprietorListGetResponseDtos;
    }

    @Transactional
    public void removeList(List<Integer> idList) {
        repository.deleteByIdIn(idList);
    }


    public void changeArchiveStatus(List<Integer> proprietorIds) {
        List<Proprietor> proprietors = repository.findAllById(proprietorIds);
        proprietors.forEach(User::changeArchiveStatus);
        repository.saveAll(proprietors);
    }


    public ProprietorPutRequestDTO findProprietorById(Integer id) {
        Proprietor proprietor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proprietor not found"));

        ProprietorPutRequestDTO proprietorDTO = modelMapper.map(proprietor, ProprietorPutRequestDTO.class);

        if (proprietor.getImageUser() != null) {
            proprietorDTO.setImageId(proprietor.getImageUser().getId());
        }

        // Converte a entidade Realtor para o DTO
        return proprietorDTO;
    }

    // Metodo para salvar ou atualizar o Proprietário
    public Proprietor save(Proprietor proprietor) {
        return repository.save(proprietor);
    }

    // Metodo para buscar Proprietário por ID
    public Proprietor findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Proprietor not found"));
    }

    public Long getQuantityProprietor(){
        return repository.count();
    }
}
