package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CustumerService {

    private final CustumerRepository repository;
    private final ModelMapper modelMapper;


    //CERTO
    public CustomerPostRequestDTO createCustumer(
            @Valid CustomerPostRequestDTO custumerPostDTO) {
        System.out.println("Recebido no DTO: " + custumerPostDTO);


        Customer customer = modelMapper.map(custumerPostDTO, Customer.class);
        System.out.println(customer);
        Customer savedCustomer = repository.save(customer);

        System.out.println("Convertido para entidade: " + savedCustomer);

        return custumerPostDTO.convertToDTO(savedCustomer);
    }

    //certo
    public Customer editCustumer(
            @NotNull @Positive Integer id,
            @Valid CustomerPutRequestDTO custumerDTO) {

       Customer existingCustomer = repository.findById(id).orElseThrow(() ->
        new NoSuchElementException("Editor com o ID " + id + " não encontrado."));

        // Atualiza apenas os campos que vieram no DTO (mantendo os valores existentes)
       modelMapper.map(custumerDTO, existingCustomer);

       return repository.save(existingCustomer);
    }


    public void removeCustumer(
            @NotNull @Positive Integer id) {
        repository.deleteById(id);
    }

    public Page<CustomerListGetResponseDTO> findAllByFilter(CustomerFilterPostResponseDTO costumerDto, Pageable pageable) {

        Customer customer = modelMapper.map(costumerDto, Customer.class);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Customer> example = Example.of(customer, matcher);

        Page<Customer> customerList = repository.findAll(example, pageable);



        Page<CustomerListGetResponseDTO> costumerListGetResponseDtos = customerList.map(customerx ->
                modelMapper.map(customerx, CustomerListGetResponseDTO.class)
        );

        return costumerListGetResponseDtos;
    }

    @Transactional
    public void removeList(List<Integer> idList) {
        repository.deleteByIdIn(idList);

    }

    public void changeArchiveStatus(List<Integer> customerIds) {
        List<Customer> customers = repository.findAllById(customerIds);
        customers.forEach(User::changeArchiveStatus);
        repository.saveAll(customers);
    }

    public CustomerPutRequestDTO findCustomerById(Integer id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Converte a entidade Realtor para o DTO
        return modelMapper.map(customer, CustomerPutRequestDTO.class);
    }
}
