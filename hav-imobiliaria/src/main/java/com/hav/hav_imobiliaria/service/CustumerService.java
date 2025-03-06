package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CustumerService {

    private final CustumerRepository repository;
    private final ModelMapper modelMapper;


    //a principio certo
    public CustumerPostRequestDTO createCustumerOwner(
            @Valid CustumerPostRequestDTO custumerPostDTO) {
        System.out.println("Recebido no DTO: " + custumerPostDTO);


        Customer customer = modelMapper.map(custumerPostDTO, Customer.class);
        Customer savedCustomer = repository.save(customer);

        System.out.println("Convertido para entidade: "
                + savedCustomer
                + customer.getName()
                + customer.getEmail()
                + customer.getPassword()
                + customer.getCelphone()
                + customer.getCpf());

        return custumerPostDTO.convertToDTO(savedCustomer);
    }





    public Customer editCustumerOwner(
            @NotNull @Positive Integer id,
            @Valid CustumerPutRequestDTO custumerOwnerDTO) {

        if(repository.existsById(id)){
            Customer customer = custumerOwnerDTO.convert();
            customer.setId(id);
            return repository.save(customer);
        }
        throw new NoSuchElementException();
    }

    public Customer alterCustumerOwner(
            @NotNull @Positive Integer id,
            @NotNull @Positive Integer idCustumerOwner) {
        return null; //n sei como fazer
    }

    public Page<Customer> searchCustumerOwners(
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
