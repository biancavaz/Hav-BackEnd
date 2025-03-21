package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.DTO.Property.*;
import com.hav.hav_imobiliaria.model.entity.Properties.PropertyFeature;
import com.hav.hav_imobiliaria.model.entity.Properties.Taxes;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.repository.ImagePropertyRepository;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.BiConsumer;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
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
        // Buscar propriedade
        Property property = repository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Propriedade não encontrada"));

        // Atualizar campos diretamente
        property.setTitle(propertyDTO.getTitle());
        property.setPropertyDescription(propertyDTO.getPropertyDescription());
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setPurpose(propertyDTO.getPurpose());
        property.setPropertyStatus(propertyDTO.getPropertyStatus());
        property.setPrice(propertyDTO.getPrice());
        property.setArea(propertyDTO.getArea());
        property.setPromotionalPrice(propertyDTO.getPromotionalPrice());
        property.setHighlight(propertyDTO.getHighlight());
        property.setPropertyCategory(propertyDTO.getPropertyCategory());
        property.setFloors(propertyDTO.getFloors());

        // Atualizar objetos embutidos
        modelMapper.map(propertyDTO.getPropertyFeatures(), property.getPropertyFeatures());
        modelMapper.map(propertyDTO.getTaxes(), property.getTaxes());


        // Atualizar relações
        updateRealtors(property, propertyDTO.getRealtors());
        updateProprietor(property, propertyDTO.getProprietor());
        updateAdditionals(property, propertyDTO.getAdditionals());

        // Salvar a propriedade antes de processar imagens
        repository.save(property);

        // Processar imagens separadamente
        processImages(propertyId, deletedImageIds, newImages);

        return property;
    }

    private void updateRealtors(Property property, List<Integer> realtorIds) {
        if (realtorIds != null) {
            List<Realtor> realtors = realtorService.findAllById(realtorIds);
            property.setRealtors(new ArrayList<>(realtors));  // Converte para List
        }
    }

    private void updateProprietor(Property property, Integer proprietorId) {
        if (!property.getProprietor().getId().equals(proprietorId)) {
            property.setProprietor(proprietorService.findById(proprietorId));
        }
    }

    private void updateAdditionals(Property property, List<Integer> additionalIds) {
        if (additionalIds != null) {
            List<Additionals> additionals = additionalsService.findAllById(additionalIds);
            property.setAdditionals(new ArrayList<>(additionals));  // Converte para List
        }
    }

    private void processImages(Integer propertyId, List<Integer> deletedImageIds, List<MultipartFile> newImages) {
        if (deletedImageIds != null && !deletedImageIds.isEmpty()) {
            imageService.deletePropertyImages(deletedImageIds);
        }
        if (newImages != null && !newImages.isEmpty()) {
            imageService.uploadPropertyImages(propertyId, newImages);
        }
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
