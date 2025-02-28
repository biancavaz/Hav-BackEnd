package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Realtor;
import com.hav.hav_imobiliaria.repository.RealtorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RealtorService {

    private final RealtorRepository repository;

    public Realtor createRealtor(
            @Valid RealtorPostRequestDTO realtorPostDTO) {

        Realtor realtor = realtorPostDTO.convert();
        return repository.save(realtor);
    }


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

    public Page<Realtor> searchRealtors(Pageable pageable) {
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
