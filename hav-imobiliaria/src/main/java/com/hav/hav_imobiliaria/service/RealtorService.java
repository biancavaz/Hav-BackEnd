package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.RealtorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class RealtorService {

    private final RealtorRepository repository;
    private final ModelMapper modelMapper;

    public RealtorPostRequestDTO createRealtor(@Valid RealtorPostRequestDTO realtorDTO) {

        Realtor realtor = modelMapper.map(realtorDTO, Realtor.class);

        Realtor savedRealtor = repository.save(realtor);

        System.out.println(savedRealtor);
        //testando só
        System.out.println("Convertido para entidade: "
                + savedRealtor
                + realtor.getName()
                + realtor.getEmail()
                + realtor.getPassword()
                + realtor.getCelphone()
                +realtor.getCpf());

        return realtorDTO.convertToDTO(savedRealtor);
    }

    public Realtor editRealtor(
            @NotNull @Positive Integer id,
            @Valid RealtorPutRequestDTO realtorPutDTO) {

        if (repository.existsById(id)) {
            Realtor realtor = modelMapper.map(realtorPutDTO, Realtor.class);
            realtor.setId(id);
            return repository.save(realtor);
        }
        throw new NoSuchElementException("Corretor com o ID " + id + " não encontrado.");
    }


//    public Realtor editRealtor(
//            @NotNull @Positive Integer id,
//            @Valid RealtorPutRequestDTO realtorPutDTO) {
//        if(repository.existsById(id)){
//            Realtor realtor = realtorPutDTO.convert();
//            return repository.save(realtor);
//        }
//        throw new NoSuchElementException();
//    }

    public Realtor alterRealtor(
            @NotNull @Positive Integer id,
            @NotNull @Positive Integer idrealtor) {

        if (repository.existsById(id)) {
            Realtor realtor = repository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Corretor com ID " + id + " não encontrado."));

            realtor.setId(idrealtor);
            return repository.save(realtor);
        }
        throw new NoSuchElementException("Corretor com ID " + id + " não encontrado.");
    }

    public Page<Realtor> searchRealtors(
            Pageable pageable) {
        return repository.findAll(pageable);
    }

    public RealtorGetResponseDTO searchRealtor(Integer id) {
        Realtor realtor = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Realtor not found with id: " + id));
        return modelMapper.map(realtor, RealtorGetResponseDTO.class);
    }

    public void removeRealtor(
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
