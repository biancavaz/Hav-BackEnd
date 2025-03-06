package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Property.*;
import com.hav.hav_imobiliaria.model.entity.*;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository repository;
    private final AdditionalsService additionalsService;
    private final RealtorService realtorService;
    private final CustomerOwnerService customerOwnerService;
    private ModelMapper modelMapper = new ModelMapper();

    public Property create(@Valid PropertyPostRequestDTO propertyDTO) {

        Property property = propertyDTO.convert();

        List<Additionals> additionals = additionalsService.findAllyById(propertyDTO.additionalsId());
        property.setAdditionals(additionals);

        List<Realtor> realtors = realtorService.findAllById(propertyDTO.realtorsId());
        property.setRealtors(realtors);

        property.setOwner(customerOwnerService.findById(propertyDTO.ownerId()));

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
                        property.getRealtors(),
                        property.getOwner()
                ))
                .toList();

        // Retorna uma nova Page contendo os DTOs
        return new PageImpl<>(dtos, pageable, properties.getTotalElements());
    }

    public Page<PropertyListGetResponseDTO> findAllByFilter(PropertyFilterPostResponseDTO propertyDto, Pageable pageable){

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
        //tranformando o page propery pro page da dto
        Page<PropertyListGetResponseDTO> propertyListGetResponseDtos = propertyList.map(propertyx ->
                modelMapper.map(propertyx, PropertyListGetResponseDTO.class)
        );



        return propertyListGetResponseDtos;
    }
    public void delete(@Positive @NotNull Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public Property modifyProperty(@Positive @NotNull Integer id, PropertyPutRequestDTO propertyDTO) {
        if (repository.existsById(id)) {
            Property property = propertyDTO.convert();
            property.setId(id);
            return repository.save(property);
        }
        throw new NoSuchElementException();
    }
}
