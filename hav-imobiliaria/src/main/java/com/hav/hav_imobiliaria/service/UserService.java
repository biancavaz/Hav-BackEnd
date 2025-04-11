package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long getAllRegistredNumber(){
        return userRepository.count();
    }


}
