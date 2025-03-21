package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Editor.EditorPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.ImageProperty;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.DTO.Property.*;
import com.hav.hav_imobiliaria.repository.ImagePropertyRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
    private final ImagePropertyRepository imagePropertyRepository;
    private final S3Service s3Service;

    public Property create(@Valid PropertyPostRequestDTO propertyDTO, List<MultipartFile> images) {

        Property property = propertyDTO.convert();

        if (property.getAdditionals() != null && !property.getAdditionals().isEmpty()) {
            property.setAdditionals(additionalsService.findAllById(propertyDTO.additionals()));
        }

        property.setRealtors(realtorService.findAllById(propertyDTO.realtors()));

        property.setProprietor(proprietorService.findById(propertyDTO.proprietor()));

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

    @Transactional
    public Property updateProperty(
            Integer propertyId,
            PropertyPutRequestDTO propertyDTO,
            List<Integer> deletedImageIds,
            List<MultipartFile> newImages
    ) {

        // Buscar propriedade no banco
        Property property = repository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        // Atualizar dados da propriedade
        property.setTitle(propertyDTO.getTitle());
        property.setPropertyDescription(propertyDTO.getPropertyDescription());
        property.setPrice(propertyDTO.getPrice());

        repository.save(property);

        // Remover imagens antigas (se houver)
        if (deletedImageIds != null && !deletedImageIds.isEmpty()) {
            imageService.deletePropertyImages(deletedImageIds);
        }

        // Adicionar novas imagens (se houver)
        if (newImages != null && !newImages.isEmpty()) {
            imageService.uploadPropertyImages(propertyId, newImages);
        }

        return property;
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
