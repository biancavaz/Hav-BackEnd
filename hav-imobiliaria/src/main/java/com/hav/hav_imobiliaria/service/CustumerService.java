package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerFilterPostResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerListGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Customer.CustomerPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.model.entity.Users.ImageUser;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import com.hav.hav_imobiliaria.repository.ImageUserRepository;
import com.hav.hav_imobiliaria.security.modelSecurity.Role;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.UserRepositorySecurity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordGeneratorService passwordGeneratorService;
    private final EmailSenderService emailSenderService;
    private final UserRepositorySecurity userRepositorySecurity;
    private final PasswordEncoder passwordEncoder;

    public CustomerPostRequestDTO createCustumer(
            @Valid CustomerPostRequestDTO custumerPostDTO,
            MultipartFile image) {

        Customer customer = modelMapper.map(custumerPostDTO, Customer.class);

        String password = passwordGeneratorService.generateSecurePassword();
        System.out.println(password);



        UserSecurity newUserSec = new UserSecurity();

        newUserSec.setEmail(customer.getEmail());
        newUserSec.setPassword(passwordEncoder.encode(password));
        newUserSec.setName(customer.getName());
        newUserSec.setRole(Role.valueOf("CUSTOMER"));

        userRepositorySecurity.save(newUserSec);

        customer.setUserSecurity(newUserSec);

        Customer savedCustomer = repository.save(customer);

        emailSenderService.sendPasswordNewAccount(customer.getEmail(), password);



        if (image != null) {
            imageService.uploadUserImage(savedCustomer.getId(), image);
        }

        return custumerPostDTO.convertToDTO(savedCustomer);
    }

    public Customer updateCustomer(
            @NotNull @Positive Integer id,
            @Valid CustomerPutRequestDTO custumer,
            @Positive Integer deletedImageId,
            MultipartFile newImage) {

        Customer customer = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Editor com o ID " + id + " não encontrado."));

        modelMapper.map(custumer, customer);

        if (deletedImageId != null){
            imageService.deleteUserImage(deletedImageId);

        }
        if( newImage != null) {
            imageService.uploadUserImage(id, newImage);
        }

        return repository.save(customer);
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

    public CustomerPutRequestDTO searchCustumer(
            @NotNull @Positive Integer id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        CustomerPutRequestDTO customerDTO = modelMapper.map(customer, CustomerPutRequestDTO.class);

        if (customer.getImageUser() != null) {
            customerDTO.setImageId(customer.getImageUser().getId());
        }

        return customerDTO;
    }
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

    public CustomerPutRequestDTO findCustomerById(Integer id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Converte a entidade Realtor para o DTO
        return modelMapper.map(customer, CustomerPutRequestDTO.class);
    }
}
