package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPostDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.ProprietorRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProprietorService {

    private final ProprietorRepository repository;
    private final ModelMapper modelMapper;

    public ProprietorPostDTO createProprietor(@Valid ProprietorPostDTO proprietorDTO) {
        System.out.println("Recebido no DTO: " + proprietorDTO);

        // Mapeamento do DTO para entidade usando o ModelMapper
        Proprietor proprietor = modelMapper.map(proprietorDTO, Proprietor.class);

        // Salvar a entidade e retornar a resposta
        Proprietor savedproprietor = repository.save(proprietor);

        //testando só
        System.out.println("Convertido para entidade: "
                + savedproprietor
                + proprietor.getName()
                + proprietor.getEmail()
                + proprietor.getPassword()
                + proprietor.getCelphone()
                +proprietor.getCpf());

        return proprietorDTO.convertToDTO(savedproprietor);
    }

    public Proprietor findById(Integer integer) {
        return repository.findById(integer).get();
    }

//    public Proprietor createProprietor(
//            @Valid ProprietorPostDTO proprietorDTO) {
//
//        Proprietor proprietor = proprietorDTO.convert();
//    }
}
