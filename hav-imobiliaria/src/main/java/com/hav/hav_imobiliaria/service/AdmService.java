package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPostRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.repository.AdmRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdmService {

    private final AdmRepository repository;
    private final ModelMapper modelMapper;

    public AdmPostRequestDTO createAdm(
            @Valid AdmPostRequestDTO admPostDTO) {

        System.out.println("Recebido no DTO: " + admPostDTO);

        // Mapeamento do DTO para entidade usando o ModelMapper
        Adm adm = modelMapper.map(admPostDTO, Adm.class);

        // Salvar a entidade e retornar a resposta
        Adm savedadm = repository.save(adm);

        //testando só
        System.out.println("Convertido para entidade: "
                + savedadm
                + adm.getName()
                + adm.getEmail()
                + adm.getPassword()
                + adm.getCelphone()
                +adm.getCpf());

        return admPostDTO.convertToDTO(adm);
    }
}
