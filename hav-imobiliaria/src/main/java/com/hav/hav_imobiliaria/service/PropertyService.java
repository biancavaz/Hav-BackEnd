package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.DTO.Property.*;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

        property.setAdditionals(additionalsService.findAllById(propertyDTO.additionals()));

        property.setRealtors(realtorService.findAllById(propertyDTO.realtors()));

        property.setProprietor(proprietorService.findById(propertyDTO.proprietor()));

        String uniqueCode;
        do {
            uniqueCode = generateUniquePropertyCode();
        } while (repository.existsByPropertyCode(uniqueCode));
        property.setPropertyCode(uniqueCode);

        return repository.save(property);
    }

    private String generateUniquePropertyCode() {
        Random random = new Random();
        int firstNumber = random.nextInt(10); // número (0-9)
        int secondNumber = random.nextInt(10); // número (0-9)
        int thirdNumber = random.nextInt(10); // número (0-9)
        int fourthNumber = random.nextInt(10); // número (0-9)
        char letter = (char) (random.nextInt(26) + 'A'); // Letra aleatória de A-Z
        int lastNumber = random.nextInt(10);

        return String.format("%d%d%d%d%c%d", firstNumber, secondNumber, thirdNumber, fourthNumber, letter, lastNumber);
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
        System.out.println(property);

        //criando o example matcher específico dos filtros do imóvel
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        //chamando o matcher
        Example<Property> example = Example.of(property, matcher);

        Page<Property> propertyList = repository.findAll(example, pageable);

        List<Property> propertyFinal = propertyList
                .getContent().stream().filter(propertyFilterPrice ->

                        propertyFilterPrice.getPrice() >= propertyDto.getMinPric()
                                && propertyFilterPrice.getPrice() <= propertyDto.getMaxPric()

                ).collect(Collectors.toList());

        //transforme o lista para page aqui

        // Criando um novo Page a partir da lista filtrada
        Page<Property> filteredPage = new PageImpl<>(propertyFinal, pageable, propertyFinal.size());

        //tranformando o page propery pro page da dto
        Page<PropertyListGetResponseDTO> propertyListGetResponseDtos = filteredPage.map(propertyx ->
                modelMapper.map(propertyx, PropertyListGetResponseDTO.class)
        );

        return propertyListGetResponseDtos;
    }

    public Page<PropertyListGetResponseDTO> searchProperties(PropertySearchCriteria criteria, Pageable pageable) {
        // Cria a Specification com os critérios de busca
        Specification<Property> spec = PropertySpecifications.withCriteria(criteria);

        // Busca as propriedades paginadas
        Page<Property> propertyPage = repository.findAll(spec, pageable);

        // Converte a página de Property para PropertyListGetResponseDTO
        return propertyPage.map(this::convertToDTO);
    }

    private PropertyListGetResponseDTO convertToDTO(Property property) {
        PropertyListGetResponseDTO dto = new PropertyListGetResponseDTO();
        dto.setPropertyCode(property.getPropertyCode());
        dto.setPrice(Double.valueOf(String.valueOf(property.getPrice()))); // Converte o preço para String
        dto.setPropertyType(property.getPropertyType());
        dto.setPropertyCategory(property.getPropertyCategory());
        dto.setPropertyStatus(property.getPropertyStatus());
        return dto;
    }

    public Page<PropertyListWithProprietorDTO> searchPropertiesWithProprietor(PropertySearchCriteria criteria, Pageable pageable) {
        Specification<Property> spec = PropertySpecifications.withCriteria(criteria);
        Page<Property> propertyPage = repository.findAll(spec, pageable);
        return propertyPage.map(this::convertToDTOWithProprietor);
    }

    private PropertyListWithProprietorDTO convertToDTOWithProprietor(Property property) {
        PropertyListWithProprietorDTO dto = new PropertyListWithProprietorDTO();
        dto.setPropertyCode(property.getPropertyCode());
        dto.setProprietorName(property.getProprietor().getName()); // Obtém o nome do proprietário
        dto.setPropertyType(property.getPropertyType());
        dto.setPropertyCategory(property.getPropertyCategory());
        dto.setPropertyStatus(property.getPropertyStatus());
        return dto;
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

    public Property modifyProperty(@Positive @NotNull Integer id, @Valid PropertyPutRequestDTO propertyDTO) {
        if (repository.existsById(id)) {
            Property property = propertyDTO.convert();
            property.setId(id);
            return repository.save(property);
        }
        throw new NoSuchElementException();
    }
}
