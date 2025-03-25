package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Realtor.*;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.RealtorRepository;
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

@Service
@AllArgsConstructor
public class RealtorService {

    private final RealtorRepository repository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    public RealtorPostRequestDTO createRealtor(
            @Valid RealtorPostRequestDTO realtorDTO,
            MultipartFile image) {

        Realtor realtor = modelMapper.map(realtorDTO, Realtor.class);

        Realtor savedRealtor = repository.save(realtor);

        if (image != null) {
            imageService.uploadUserImage(savedRealtor.getId(), image);
        }

        return realtorDTO.convertToDTO(savedRealtor);
    }

    @Transactional
    public Realtor updateRealtor(
            @NotNull @Positive Integer id,
            @Valid RealtorPutRequestDTO realtorPutDTO,
            @Positive Integer deletedImageId,
            MultipartFile newImage) {

        Realtor realtor = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Corretor com o ID " + id + " não encontrado."));

        modelMapper.map(realtorPutDTO, realtor);

        if (deletedImageId != null) {
            imageService.deleteUserImage(deletedImageId);
        }

        if (newImage != null) {
            imageService.uploadUserImage(id, newImage);
        }

        return repository.save(realtor);
    }

    public Page<RealtorListGetResponseDTO> findAllByFilter(
            @Valid RealtorFilterPostResponseDTO realtorDTO,
            Pageable pageable) {

        System.out.printf(realtorDTO.toString());
        Realtor realtor = modelMapper.map(realtorDTO, Realtor.class);

        // Criar o exemplo de filtro usando o RealtorMatcher
//criando o example matcher específico dos filtros do imóvel
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        // Chama o repositório usando o exemplo com filtro
        Example<Realtor> example = Example.of(realtor, matcher);

        Page<Realtor> realtorPage = repository.findAll(example, pageable);


        // Transformar o resultado final na DTO que será retornado
        Page<RealtorListGetResponseDTO> realtorListGetResponseDTOS = realtorPage.map(realtorx ->
                modelMapper.map(realtorx, RealtorListGetResponseDTO.class)
        );

        return realtorListGetResponseDTOS;
    }

    @Transactional
    public void removeList(List<Integer> idList) {
        repository.deleteByIdIn(idList);
    }

    public void changeArchiveStatus(List<Integer> realtorIds) {
        List<Realtor> realtors = repository.findAllById(realtorIds);
        realtors.forEach(User::changeArchiveStatus);
        repository.saveAll(realtors);
    }

//    public Realtor alterRealtor(
//            @NotNull @Positive Integer id,
//            @NotNull @Positive Integer idrealtor,
//            @RequestBody RealtorPatchRequestDTO realtorDTO) {
//
//        if (repository.existsById(id)) {
//
//            // Recupera a entidade existente do banco de dados
//            Realtor realtor = repository.findById(id)
//                    .orElseThrow(() -> new NoSuchElementException("Corretor com ID " + id + " não encontrado."));
//
//            // Atualiza os campos da entidade usando ModelMapper, ignorando os campos nulos
//            modelMapper.map(realtorDTO, realtor);
//
//
//            // Altere o idrealtor, que aparentemente é um campo de atualização
//            realtor.setId(idrealtor);
//
//            // Salve a entidade atualizada de volta ao repositório
//            return repository.save(realtor);
//        }
//
//        throw new NoSuchElementException("Corretor com ID " + id + " não encontrado.");
//    }

    //
//    public Page<Realtor> searchRealtors(
//            Pageable pageable) {
//        return repository.findAll(pageable);
//    }
//
//    public RealtorGetResponseDTO searchRealtor(Integer id) {
//        Realtor realtor = repository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Realtor not found with id: " + id));
//        return modelMapper.map(realtor, RealtorGetResponseDTO.class);
//    }
//
//
//    public void removeRealtor(
//            @NotNull @Positive Integer id) {
//        repository.deleteById(id);
//    }
//
//
//
    public List<Realtor> findAllById(List<Integer> integers) {
        if (integers == null || integers.isEmpty()) {
            return null;
        }
        return repository.findAllById(integers);
    }

    public RealtorPutRequestDTO findRealtorById(Integer id) {
        Realtor realtor = repository.findById(id).get();

        // Converte a entidade Realtor para o DTO
        return modelMapper.map(realtor, RealtorPutRequestDTO.class);
    }

}
