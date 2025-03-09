package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Adm.AdmPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Adm;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.repository.AdmRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AdmService {

    private final AdmRepository repository;
    private final ModelMapper modelMapper;

    //certo
    public AdmPostRequestDTO createAdm(
            @Valid AdmPostRequestDTO admPostDTO) {

        System.out.println("Recebido no DTO: " + admPostDTO);

        // Mapeamento do DTO para entidade usando o ModelMapper
        Adm adm = modelMapper.map(admPostDTO, Adm.class);

        // Salvar a entidade e retornar a resposta
        Adm savedadm = repository.save(adm);

        //testando só
        System.out.println("Convertido para entidade: "
                + savedadm);

        return admPostDTO.convertToDTO(adm);
    }

    //certo
    public Adm editAdm(
            @Positive @NotNull Integer id,
            @Valid AdmPutRequestDTO admPutDTO) {

        Adm existingAdm = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Editor com o ID " + id + " não encontrado."));

        // Atualiza apenas os campos que vieram no DTO (mantendo os valores existentes)
        modelMapper.map(admPutDTO, existingAdm);

        return repository.save(existingAdm);
    }
}
