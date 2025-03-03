package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.RealtorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RealtorService {

    private final RealtorRepository repository;
    private final ModelMapper modelMapper;

    public RealtorPostRequestDTO createRealtor(RealtorPostRequestDTO realtorDTO) {
        // Converter DTO para entidade usando Builder
        Realtor realtor = Realtor.builder()
                .name(realtorDTO.name())
                .email(realtorDTO.email())
                .password(realtorDTO.password())
                .cpf(realtorDTO.cpf())
                .celphone(realtorDTO.phone())
                .creci(realtorDTO.creci())
                .build();

        Realtor savedRealtor = repository.save(realtor);
        return modelMapper.map(savedRealtor, RealtorPostRequestDTO.class);
    }

//    public Realtor createRealtor(
//            @Valid RealtorPostRequestDTO realtorPostDTO) {
//
//        Realtor realtor = realtorPostDTO.convert();
//        return repository.save(realtor);
//    }


    public Realtor editRealtor(
            @NotNull @Positive Integer id,
            @Valid RealtorPutRequestDTO realtorPutDTO) {
        if(repository.existsById(id)){
            Realtor realtor = realtorPutDTO.convert();
            return repository.save(realtor);
        }
        throw new NoSuchElementException();
    }


    public Realtor alterRealtor(
            @NotNull @Positive Integer id,
            @NotNull @Positive Integer idrealtor) {
        return null;
        //n sei como fazer
    }

    public Page<Realtor> searchRealtors(
            Pageable pageable) {
        return repository.findAll(pageable);
    }

    public void remove(
            @NotNull @Positive Integer id) {
        repository.deleteById(id);
    }

//    public Realtor searchRealtor(Integer id) {
//        return repository.findById(id).orElseThrow(NoSuchElementException::new);
//    }


}
