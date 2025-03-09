package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyListGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        System.out.println(customer);
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

    public Customer alterCustomer(
            @NotNull @Positive Integer id,
            @NotNull @Positive Integer idCustumer) {
        return null; //n sei como fazer
    }

    public Page<Customer> searchCustumers(
            Pageable pageable) {
        return repository.findAll(pageable);
    }


//    public Realtor searchCustumerOwner(
//            @NotNull @Positive Integer id) {
//    }

    public void removeCustumer(
            @NotNull @Positive Integer id) {
        repository.deleteById(id);
    }

    public Page<CustomerListGetResponseDTO> findAllByFilter(CustomerFilterPostResponseDTO costumerDto, Pageable pageable) {

        Customer customer = modelMapper.map(costumerDto, Customer.class);

        //criando o example matcher específico dos filtros do imóvel
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        //chamando o matcher
        Example<Customer> example = Example.of(customer, matcher);

        Page<Customer> customerList = repository.findAll(example, pageable);



        //tranformando o page propery pro page da dto
        Page<CustomerListGetResponseDTO> costumerListGetResponseDtos = customerList.map(customerx ->
                modelMapper.map(customerx, CustomerListGetResponseDTO.class)
        );

        return costumerListGetResponseDtos;
    }
}
