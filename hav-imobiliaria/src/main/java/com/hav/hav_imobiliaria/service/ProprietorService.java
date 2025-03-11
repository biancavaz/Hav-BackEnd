package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPostDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.ProprietorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProprietorService {

    private final ProprietorRepository repository;
    private final ModelMapper modelMapper;


    //certo
    public ProprietorPostDTO createProprietor(@Valid ProprietorPostDTO proprietorDTO) {
        System.out.println("Recebido no DTO: " + proprietorDTO);

        // Mapeamento do DTO para entidade usando o ModelMapper
        Proprietor proprietor = modelMapper.map(proprietorDTO, Proprietor.class);

        // Salvar a entidade e retornar a resposta
        Proprietor savedproprietor = repository.save(proprietor);

        //testando só
        System.out.println("Convertido para entidade: "
                + savedproprietor);

        return proprietorDTO.convertToDTO(savedproprietor);
    }

    //certo
    public Proprietor editProprietor(
            @Positive @NotNull Integer id,
            @Valid ProprietorPutRequestDTO proprietorPutDTO) {

        Proprietor existingProprietor = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Proprietario com o ID " + id + " não encontrado."));

        // Atualiza apenas os campos que vieram no DTO (mantendo os valores existentes)
        modelMapper.map(proprietorPutDTO, existingProprietor);

        return repository.save(existingProprietor);
    }

    public Page<ProprietorListGetResponseDTO> findAllByFilter(Pageable pageable, ProprietorFilterPostResponseDTO proprietorDto){
        Proprietor proprietor = modelMapper.map(proprietorDto, Proprietor.class);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Proprietor> example = Example.of(proprietor, matcher);

        Page<Proprietor> proprietorList = repository.findAll(example, pageable);



        Page<ProprietorListGetResponseDTO> proprietorListGetResponseDtos = proprietorList.map(proprietorx ->
                modelMapper.map(proprietorx, ProprietorListGetResponseDTO.class)
        );

        return proprietorListGetResponseDtos;
    }

    public Proprietor findById(Integer integer) {
        return repository.findById(integer).get();
    }

//    public Proprietor createProprietor(
//            @Valid ProprietorPostDTO proprietorDTO) {
//
//        Proprietor proprietor = proprietorDTO.convert();
//    }
}
