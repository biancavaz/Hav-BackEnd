package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.matcher.RealtorMather;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.*;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.RealtorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RealtorService {

    private final RealtorRepository repository;
    private final ModelMapper modelMapper;
    private final RealtorMather realtorMatcher;

    //certo
    public RealtorPostRequestDTO createRealtor(
            @Valid RealtorPostRequestDTO realtorDTO) {

        // Usando o ModelMapper para converter o DTO (realtorDTO) para uma entidade (Realtor).
        Realtor realtor = modelMapper.map(realtorDTO, Realtor.class);

        // Salvando a entidade convertida no banco de dados através do repositório.
        Realtor savedRealtor = repository.save(realtor);

        System.out.println(savedRealtor);
        //testando só
        System.out.println("Convertido para entidade: "
                + savedRealtor
                +savedRealtor.getName()
                +savedRealtor.getEmail()
                +savedRealtor.getCpf()
                + savedRealtor.getCelphone()
                + savedRealtor.getCreci()
                + savedRealtor.getAddress());

        return realtorDTO.convertToDTO(savedRealtor);
    }


    //consegui tbbbb ihuuul
    public Realtor editRealtor(
            @NotNull @Positive Integer id,
            @Valid RealtorPutRequestDTO realtorPutDTO) {

        Realtor existingRealtor = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Corretor com o ID " + id + " não encontrado."));

        // Atualiza apenas os campos que vieram no DTO (mantendo os valores existentes)
        modelMapper.map(realtorPutDTO, existingRealtor);

        return repository.save(existingRealtor);
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

        // Aqui você pode aplicar filtros adicionais, como preço (se houver)
        List<Realtor> filteredRealtors = realtorPage.getContent(); // A lista de corretores

        // Criar uma nova página filtrada
        Page<Realtor> filteredPage = new PageImpl<>(filteredRealtors, pageable, filteredRealtors.size());

        System.out.printf(filteredPage.getContent().getFirst().toString());

        // Transformar o resultado final na DTO que será retornado
        Page<RealtorListGetResponseDTO> realtorListGetResponseDTOS = filteredPage.map(realtorx ->
                modelMapper.map(realtorx, RealtorListGetResponseDTO.class)
        );

        return realtorListGetResponseDTOS;
    }
    @Transactional
    public void removeList(List<Integer> idList) {
        repository.deleteByIdIn(idList);
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
//    public List<Realtor> findAllById(List<Integer> integers) {
//        if (integers == null || integers.isEmpty()) {
//            return null;
//        }
//        return repository.findAllById(integers);
//    }

//    public Realtor searchRealtor(Integer id) {
//        return repository.findById(id).orElseThrow(NoSuchElementException::new);
//    }


}
