package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Address;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.AddressRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository repository;
    private final ModelMapper modelMapper;

//
//    public Address create(
//            @Valid AddressPostRequestDTO addressDTO) {
//        return repository.save(addressDTO.convert());
//    }

    public Address editAddress(
            @NotNull @Positive Integer id,
            @Valid AddressPutRequestDTO addressDTO) {

        Address existingAddress = repository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Enderço com o ID " + id + " não encontrado."));

        // Atualiza apenas os campos que vieram no DTO (mantendo os valores existentes)
        modelMapper.map(addressDTO, existingAddress);

        return repository.save(existingAddress);
    }


}
