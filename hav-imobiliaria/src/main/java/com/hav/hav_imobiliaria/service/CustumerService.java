package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Custumer.CustumerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Custumer.CustumerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Custumer;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CustumerService {

    private final CustumerRepository repository;


    public Custumer createCustumerOwner(
            @Valid CustumerPostRequestDTO custumerOwnerPostDTO) {

        Custumer custumer = custumerOwnerPostDTO.convert();
        return repository.save(custumer);
    }

    public Custumer editCustumerOwner(
            @NotNull @Positive Integer id,
            @Valid CustumerPutRequestDTO custumerOwnerDTO) {

        if(repository.existsById(id)){
            Custumer custumer = custumerOwnerDTO.convert();
            custumer.setId(id);
            return repository.save(custumer);
        }
        throw new NoSuchElementException();
    }

    public Custumer alterCustumerOwner(
            @NotNull @Positive Integer id,
            @NotNull @Positive Integer idCustumerOwner) {
        return null; //n sei como fazer
    }

    public Page<Custumer> searchCustumerOwners(
            Pageable pageable) {
        return repository.findAll(pageable);
    }


//    public Realtor searchCustumerOwner(
//            @NotNull @Positive Integer id) {
//    }

    public void removeCustumerOwner(
            @NotNull @Positive Integer id) {
        repository.deleteById(id);
    }

}
