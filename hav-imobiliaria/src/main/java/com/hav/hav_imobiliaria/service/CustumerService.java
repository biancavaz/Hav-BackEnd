package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustumerPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
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


    //CERTO
    public CustumerPostRequestDTO createCustumer(
            @Valid CustumerPostRequestDTO custumerPostDTO) {
        System.out.println("Recebido no DTO: " + custumerPostDTO);


        Customer customer = modelMapper.map(custumerPostDTO, Customer.class);
        Customer savedCustomer = repository.save(customer);

        System.out.println("Convertido para entidade: " + savedCustomer);

        return custumerPostDTO.convertToDTO(savedCustomer);
    }

    //certo
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

}
