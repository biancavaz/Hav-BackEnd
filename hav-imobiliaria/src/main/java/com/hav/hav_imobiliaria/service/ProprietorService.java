package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPostDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.ProprietorRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class ProprietorService {


    private ProprietorRepository repository;

//    public Proprietor createProprietor(
//            @Valid ProprietorPostDTO proprietorDTO) {
//
//        Proprietor proprietor = proprietorDTO.convert();
//    }
}
