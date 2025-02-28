package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.CustumerOwner.CustumerOwnerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.CustumerOwner.CustumerOwnerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.CustumerOwner;
import com.hav.hav_imobiliaria.model.entity.Realtor;
import com.hav.hav_imobiliaria.repository.CustumerOwnerRepository;
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
public class CustumerOwnerService {

    private final CustumerOwnerRepository repository;


    public CustumerOwner createCustumerOwner(
            @Valid CustumerOwnerPostRequestDTO custumerOwnerPostDTO) {

        CustumerOwner custumerOwner = custumerOwnerPostDTO.convert();
        return repository.save(custumerOwner);
    }

    public CustumerOwner editCustumerOwner(
            @NotNull @Positive Integer id,
            @Valid CustumerOwnerPutRequestDTO custumerOwnerDTO) {

        if(repository.existsById(id)){
            CustumerOwner custumerOwner = custumerOwnerDTO.convert();
            custumerOwner.setId(id);
            return repository.save(custumerOwner);
        }
        throw new NoSuchElementException();
    }

    public CustumerOwner alterCustumerOwner(
            @NotNull @Positive Integer id,
            @NotNull @Positive Integer idCustumerOwner) {
        return null; //n sei como fazer
    }

    public Page<CustumerOwner> searchCustumerOwners(
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
