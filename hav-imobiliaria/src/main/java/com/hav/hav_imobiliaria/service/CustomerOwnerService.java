package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.CustumerOwner.CustomerOwnerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.CustumerOwner.CustomerOwnerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
import com.hav.hav_imobiliaria.repository.CustomerOwnerRepository;
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
public class CustomerOwnerService {

    private final CustomerOwnerRepository repository;


    public CustomerOwner createCustomerOwner(
            @Valid CustomerOwnerPostRequestDTO customerOwnerPostDTO) {

        CustomerOwner customerOwner = customerOwnerPostDTO.convert();
        return repository.save(customerOwner);
    }

    public CustomerOwner editCustomerOwner(
            @NotNull @Positive Integer id,
            @Valid CustomerOwnerPutRequestDTO customerOwnerDTO) {

        if (repository.existsById(id)) {
            CustomerOwner customerOwner = customerOwnerDTO.convert();
            customerOwner.setId(id);
            return repository.save(customerOwner);
        }
        throw new NoSuchElementException();
    }

    public CustomerOwner alterCustomerOwner(
            @NotNull @Positive Integer id,
            @NotNull @Positive Integer idCustomerOwner) {
        return null; //n sei como fazer
    }

    public Page<CustomerOwner> searchCustomerOwners(
            Pageable pageable) {
        return repository.findAll(pageable);
    }


//    public Realtor searchCustomerOwner(
//            @NotNull @Positive Integer id) {
//    }

    public void removeCustomerOwner(
            @NotNull @Positive Integer id) {
        repository.deleteById(id);
    }

    public CustomerOwner findById(@Positive @NotNull Integer integer) {
        return repository.findById(integer).get();
    }
}
