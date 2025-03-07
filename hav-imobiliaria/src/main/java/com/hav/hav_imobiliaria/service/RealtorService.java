package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.User.Realtor;
import com.hav.hav_imobiliaria.repository.RealtorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RealtorService {

    private final RealtorRepository repository;
    private final UserService userService;

    public Realtor createRealtor(@Valid RealtorPostRequestDTO realtorDTO) {
       Realtor realtor = realtorDTO.convert();
       return repository.save(realtor);
    }


    public Realtor editRealtor(
            @NotNull @Positive Integer id,
            @Valid RealtorPutRequestDTO realtorPutDTO) {
        if (repository.existsById(id)) {
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

    public List<Realtor> findAllById(List<Integer> integers) {
        if (integers == null || integers.isEmpty()) {
            return null;
        }
        return repository.findAllById(integers);
    }

//    public Realtor searchRealtor(Integer id) {
//        return repository.findById(id).orElseThrow(NoSuchElementException::new);
//    }


}
