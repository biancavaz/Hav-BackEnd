package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProprietorRepository proprietorRepository;
    private final RealtorRepository realtorRepository;
    private final AdmRepository admRepository;
    private final EditorRepository editorRepository;

    public Long getAllRegistredNumber(){
        return userRepository.count();
    }

    public Long getPercentageProprietors(){
        Long proprietors = proprietorRepository.count();
        Long users = userRepository.count();
        return proprietors * 100 / users;
    }

    public Long getQuantityClients(){
        Long proprietors = proprietorRepository.count();
        Long realtors = realtorRepository.count();
        Long adms = admRepository.count();
        Long editors = editorRepository.count();
        long notClients = proprietors + realtors + adms + editors;
        long allUsers = userRepository.count();
        long quantityClients = allUsers - notClients;
        return quantityClients;
    }

    public Long getPercentageClients(){
        Long proprietors = proprietorRepository.count();
        Long realtors = realtorRepository.count();
        Long adms = admRepository.count();
        Long editors = editorRepository.count();
        Long notClients = proprietors + realtors + adms + editors;
        long allUsers = userRepository.count();
        long quantityClients = allUsers - notClients;
        return (quantityClients * 100) / allUsers;
    }
}
