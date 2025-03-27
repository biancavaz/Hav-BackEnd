package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.DTO.Property.*;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
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
    private final ImageService imageService;

    public Property create(@Valid PropertyPostRequestDTO propertyDTO, List<MultipartFile> images) {

        Property property = propertyDTO.convert();

        if(property.getAdditionals() != null && property.getAdditionals().size() > 0) {
            property.setAdditionals(additionalsService.findAllById(propertyDTO.additionals()));

        }

        property.setRealtors(realtorService.findAllById(propertyDTO.realtors()));

        property.setProprietor(proprietorService.findById(propertyDTO.proprietor()));

        property.setAdditionals(additionalsService.findAllById(propertyDTO.additionals()));

        property.setPropertyCode(generateUniqueCode());

        Property savedProperty = repository.save(property);

        if (images != null && !images.isEmpty()) {
            imageService.uploadPropertyImages(savedProperty.getId(), images);
        }

        return savedProperty;
    }

    private String generateUniqueCode() {
        long timestamp = Instant.now().toEpochMilli();
        return String.valueOf(timestamp);
    }

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


        Integer bedRoom = null;
        Integer bathRoom = null;
        Integer garageSpace = null;
        Integer suite = null;

        if (propertyDto.getPropertyFeatures() != null &&
                Objects.equals(propertyDto.getPropertyFeatures().getBedRoom(), 5)) {

                bedRoom = propertyDto.getPropertyFeatures().getBedRoom();
                propertyDto.getPropertyFeatures().setBedRoom(null);

        }
        if (propertyDto.getPropertyFeatures() != null &&
                Objects.equals(propertyDto.getPropertyFeatures().getBathRoom(), 5)) {
            bathRoom = propertyDto.getPropertyFeatures().getBathRoom();
            propertyDto.getPropertyFeatures().setBathRoom(null);

        }
        if (propertyDto.getPropertyFeatures() != null &&
                Objects.equals(propertyDto.getPropertyFeatures().getGarageSpace(), 5)) {


            garageSpace = propertyDto.getPropertyFeatures().getGarageSpace();
            propertyDto.getPropertyFeatures().setGarageSpace(null);

        }
        if (propertyDto.getPropertyFeatures() != null &&
                Objects.equals(propertyDto.getPropertyFeatures().getSuite(), 5)) {
            suite = propertyDto.getPropertyFeatures().getSuite();
            propertyDto.getPropertyFeatures().setSuite(null);

        }


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
                                                && propertyPrice.getPrice() <= propertyDto.getMaxPric()) // Compare prices
                .collect(Collectors.toList());


        if(propertyDto.getPropertyFeatures() !=null){
            propertyDto.getPropertyFeatures().setBedRoom(bedRoom);
            propertyDto.getPropertyFeatures().setBathRoom(bathRoom);
            propertyDto.getPropertyFeatures().setGarageSpace(garageSpace);
            propertyDto.getPropertyFeatures().setSuite(suite);
        }


        if (propertyDto.getPropertyFeatures() != null &&
                Objects.equals(propertyDto.getPropertyFeatures().getBedRoom(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getBedRoom() >=5).collect(Collectors.toList());
        }
        if (propertyDto.getPropertyFeatures() != null &&
                Objects.equals(propertyDto.getPropertyFeatures().getBathRoom(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getBathRoom() >=5).collect(Collectors.toList());
        }
        if (propertyDto.getPropertyFeatures() != null &&
                Objects.equals(propertyDto.getPropertyFeatures().getGarageSpace(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getGarageSpace() >=5).collect(Collectors.toList());
        }
        if (propertyDto.getPropertyFeatures() != null &&
                Objects.equals(propertyDto.getPropertyFeatures().getSuite(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getSuite() >=5).collect(Collectors.toList());
        }




        //transforme o lista para page aqui

        int start = pageable.getPageNumber() * pageable.getPageSize(); // Calculando o índice de início
        int end = Math.min(start + pageable.getPageSize(), filteredAllProperties.size());


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

    public Property modifyProperty(@Positive @NotNull Integer id, @Valid PropertyPutRequestDTO propertyDTO) {
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

    public PropertyPutRequestDTO findPropertyById(Integer id) {
        Property property = repository.findById(id).get();

        // Converte a entidade Realtor para o DTO
        return modelMapper.map(property, PropertyPutRequestDTO.class);
    }
}
