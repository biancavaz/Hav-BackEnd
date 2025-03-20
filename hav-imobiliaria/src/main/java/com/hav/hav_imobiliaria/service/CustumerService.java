package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CustumerService {

    private final CustumerRepository repository;
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    public CustumerPostRequestDTO createCustumer(
            @Valid CustumerPostRequestDTO custumerPostDTO,
            MultipartFile image) {

        Customer customer = modelMapper.map(custumerPostDTO, Customer.class);

        Customer savedCustomer = repository.save(customer);

        if (image != null) {
            imageService.uploadImages(savedCustomer.getId(), image);
        }

        return custumerPostDTO.convertToDTO(savedCustomer);
    }

    public Customer editCustumer(
            @NotNull @Positive Integer id,
            @Valid CustumerPutRequestDTO custumerDTO) {

       Customer existingCustomer = repository.findById(id).orElseThrow(() ->
        new NoSuchElementException("Editor com o ID " + id + " não encontrado."));

        // Atualiza apenas os campos que vieram no DTO (mantendo os valores existentes)
       modelMapper.map(custumerDTO, existingCustomer);

       return repository.save(existingCustomer);
    }



//    public Customer alterCustomer(
//            @NotNull @Positive Integer id,
//            @NotNull @Positive Integer idCustumer) {
//        return null; //n sei como fazer
//    }
//
//    public Page<Customer> searchCustumers(
//            Pageable pageable) {
//        return repository.findAll(pageable);
//    }
//
//
////    public Realtor searchCustumer(
////            @NotNull @Positive Integer id) {
////    }
//
//    public void removeCustumer(
//            @NotNull @Positive Integer id) {
//        repository.deleteById(id);
//    }

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
}
