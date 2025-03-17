package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.DTO.Property.*;
import com.hav.hav_imobiliaria.model.entity.Users.Editor;
import com.hav.hav_imobiliaria.model.entity.Users.Proprietor;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;
    private final AdditionalsService additionalsService;
    private final RealtorService realtorService;
    private final ProprietorService proprietorService;
    private final ModelMapper modelMapper;
    private final AddressService addressService;
    private final PropertyFeatureService propertyFeatureService;
    private final TaxesService taxesService;



    public Property create(@Valid PropertyPostRequestDTO propertyDTO) {

        Property property = propertyDTO.convert();
        if(property.getAdditionals() != null && property.getAdditionals().size() > 0) {
            property.setAdditionals(additionalsService.findAllById(propertyDTO.additionals()));

        }

        property.setRealtors(realtorService.findAllById(propertyDTO.realtors()));

        property.setProprietor(proprietorService.findById(propertyDTO.proprietor()));

        property.setPropertyCode(generateUniqueCode());

        return repository.save(property);
    }

    private String generateUniqueCode() {
        long timestamp = Instant.now().toEpochMilli();
        return String.valueOf(timestamp);
    }

    //select imóveis
    public Page<PropertyGetResponseDTO> findAll(Pageable pageable) {
        // Busca todas as propriedades no banco de dados com paginação
        Page<Property> properties = repository.findAll(pageable);

        // Mapeia os objetos Property para PropertyGetResponseDTO manualmente
        List<PropertyGetResponseDTO> dtos = properties.getContent().stream()
                .map(property -> new PropertyGetResponseDTO(
                        property.getPropertyCode(),
                        property.getPropertyType(),
                        property.getPropertyStatus(),
                        property.getPurpose(),
                        property.getRealtors().stream()
                                .map(realtor -> new RealtorGetResponseDTO(
                                        realtor.getName(),
                                        realtor.getEmail(),
                                        realtor.getCpf(),
                                        realtor.getCelphone(),
                                        realtor.getCreci()
                                ))
                                .toList(),
                        new ProprietorGetResponseDTO(
                                property.getProprietor().getName(),
                                property.getProprietor().getEmail(),
                                property.getProprietor().getCpf()
                        )
                ))
                .toList();

        // Retorna uma nova Page contendo os DTOs
        return new PageImpl<>(dtos, pageable, properties.getTotalElements());
    }

    public Page<PropertyListGetResponseDTO> findAllByFilter(
            @Valid PropertyFilterPostResponseDTO propertyDto,
            Pageable pageable) {

        Property property = modelMapper.map(propertyDto, Property.class);

        //criando o example matcher específico dos filtros do imóvel
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        //chamando o matcher
        Example<Property> example = Example.of(property, matcher);


        List<Property> allProperties = repository.findAll(example);



        List<Property> filteredAllProperties = allProperties.stream()
                .filter(propertyPrice -> propertyPrice.getPrice() >= propertyDto.getMinPric()
                                                && propertyPrice.getPrice() <= propertyDto.getMaxPric() ) // Compare prices
                .collect(Collectors.toList());

        //transforme o lista para page aqui

        int start = pageable.getPageNumber() * pageable.getPageSize(); // Calculando o índice de início
        int end = Math.min(start + pageable.getPageSize(), filteredAllProperties.size());

        System.out.println(start);
        System.out.println(end);

        List<Property> pageProperty = filteredAllProperties.subList(start, end);

        // Criando um novo Page a partir da lista filtrada
        Page<Property> propertyFinal = new PageImpl<>(pageProperty, pageable, filteredAllProperties.size());

        //tranformando o page propery pro page da dto
        Page<PropertyListGetResponseDTO> propertyListGetResponseDtos = propertyFinal.map(propertyx ->
                modelMapper.map(propertyx, PropertyListGetResponseDTO.class)
        );

        return propertyListGetResponseDtos;
    }



    public void delete(@Positive @NotNull Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }


    public void deleteByPropertyCode(@NotNull String propertyCode) {
        if (repository.existsByPropertyCode(propertyCode)) {
            repository.deleteByPropertyCode(propertyCode);
        }
    }

    public void deletePropertiesByPropertyCode(@NotNull List<String> propertyCodes) {
        if (repository.findByPropertyCodeIn(propertyCodes) == null ||
                repository.findByPropertyCodeIn(propertyCodes).isEmpty()) {
            repository.deleteByPropertyCodeIn(propertyCodes);
        }
    }

    //modelmapper
    public Property modifyProperty(
            @Positive @NotNull Integer id,
            @Valid PropertyPutRequestDTO propertyDTO) {

        if (repository.existsById(id)) {
            //mapear o DTO para a entidade Property
            Property property = modelMapper.map(propertyDTO, Property.class);
            property.setId(id);  // Atribui o ID manualmente
            return repository.save(property);
        }
        throw new NoSuchElementException();
    }

    @Transactional
    public void removeList(List<Integer> idList) {
        repository.deleteByIdIn(idList);

    }

    public void changeArchiveStatus(List<Integer> propertuyIds) {
        List<Property> properties = repository.findAllById(propertuyIds);
        properties.forEach(Property::changeArchiveStatus);
        repository.saveAll(properties);
    }
}
