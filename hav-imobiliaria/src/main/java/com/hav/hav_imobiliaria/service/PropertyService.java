package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationDTO;
import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Additionals.AdditionalsGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Address.AddressPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeatureCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeaturePutRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeatureSpecifiGetRespondeDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Proprietor.ProprietorPropertySpecificGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorGetResponseDTOwithId;
import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPropertySpecificGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Taxes.TaxesPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Additionals;
import com.hav.hav_imobiliaria.model.entity.Properties.ImageProperty;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.DTO.Property.*;
import com.hav.hav_imobiliaria.model.entity.Properties.Taxes;
import com.hav.hav_imobiliaria.model.entity.Scheduling.Schedules;
import com.hav.hav_imobiliaria.model.entity.Users.Realtor;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.ImagePropertyRepository;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import com.hav.hav_imobiliaria.repository.UserRepository;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
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
    private final ImagePropertyRepository imagePropertyRepository;
    private final S3Service s3Service;
    private final SchedulesService schedulesService;
    private final UserService userService;
    private final UserRepository userRepository;

    public PropertyListGetResponseDTO create(@Valid PropertyPostRequestDTO propertyDTO, List<MultipartFile> images) {

        Property property = propertyDTO.convert();

        property.setAdditionals(additionalsService.findAllById(propertyDTO.additionals()));

        property.setRealtors(realtorService.findAllById(propertyDTO.realtors()));

        property.setProprietor(proprietorService.findById(propertyDTO.proprietor()));

        property.setPropertyCode(generateUniqueCode());

        Property savedProperty = repository.save(property);

        if (images != null && !images.isEmpty()) {
            imageService.uploadPropertyImages(savedProperty.getId(), images);
        }

        // enviar notificação da nova propriedade salva ->
        userService.checkAndNotifyUsersAboutNewProperty(savedProperty);

        return modelMapper.map(savedProperty, PropertyListGetResponseDTO.class);
    }

    private String generateUniqueCode() {
        long timestamp = Instant.now().toEpochMilli();
        return String.valueOf(timestamp);
    }

    public Page<PropertyGetResponseDTO> findAll(Pageable pageable) {
        // Busca todas as propriedades no banco de dados com paginação
        Page<Property> properties = repository.findAll(pageable);

        // Mapeia os objetos Property para PropertyGetResponseDTO manualmente
        List<PropertyGetResponseDTO> dtos =
                properties.getContent().stream().map(property ->
                        new PropertyGetResponseDTO(property.getPropertyCode(),
                                property.getPropertyType(), property.getPropertyStatus(),
                                property.getPurpose(), property.getRealtors().stream()
                                .map(realtor ->
                                        new RealtorGetResponseDTO(realtor.getName(),
                                                realtor.getEmail(), realtor.getCpf(),
                                                realtor.getCellphone(),
                                                realtor.getCreci())).toList(),
                                new ProprietorGetResponseDTO(property.getProprietor().getName(), property.getProprietor().getEmail(), property.getProprietor().getCpf()))).toList();

        // Retorna uma nova Page contendo os DTOs
        return new PageImpl<>(dtos, pageable, properties.getTotalElements());
    }

    public PropertyGetSpecificResponseDTO findPropertySpecificById(Integer id) {
        Property property = repository.findById(id).get();
        List<Integer> imageIds = new ArrayList<>();

        for (ImageProperty image : property.getImageProperties()) {
            if (image.getMainImage()) {
                imageIds.add(0, image.getId()); // Adiciona no início se for imagem principal
            } else {
                imageIds.add(image.getId()); // Adiciona normalmente
            }
        }

        List<byte[]> imageBytesList = imageService.getPropertyImages(imageIds);

        List<String> imagesString = imageBytesList.stream()
                .map(bytes -> Base64.getEncoder().encodeToString(bytes))
                .toList();

        ProprietorPropertySpecificGetResponseDTO proprietorDto = null;
        try {
            String mainImage = Base64.getEncoder().encodeToString(imageService.getUserImage(property.getProprietor().getImageUser().getId()));
            proprietorDto = new ProprietorPropertySpecificGetResponseDTO(mainImage, property.getProprietor().getName(),
                    property.getProprietor().getEmail(), property.getProprietor().getPhoneNumber());
        } catch (Exception e) {
            System.err.println(e);
            proprietorDto = new ProprietorPropertySpecificGetResponseDTO(null, property.getProprietor().getName(),
                    property.getProprietor().getEmail(), property.getProprietor().getPhoneNumber());

        }


        List<RealtorPropertySpecificGetResponseDTO> realtors = new ArrayList<>();
        for (Realtor realtor : property.getRealtors()) {
            if (realtor.getImageUser() !=null) {
                byte[] img = imageService.getUserImage(realtor.getImageUser().getId());

                String imgsString = Base64.getEncoder().encodeToString(img);

                realtors.add(new RealtorPropertySpecificGetResponseDTO(
                        realtor.getId(),
                        realtor.getName(),
                        realtor.getEmail(),
                        realtor.getCreci(),
                        realtor.getPhoneNumber(),
                        imgsString
                        ));
            } else {
                realtors.add(new RealtorPropertySpecificGetResponseDTO(
                        realtor.getId(),
                        realtor.getName(),
                        realtor.getEmail(),
                        realtor.getCreci(),
                        realtor.getPhoneNumber(),
                        null
                )); // Adiciona normalmente
            }
        }



        // Agora passa 17 argumentos, incluindo imagens
        PropertyGetSpecificResponseDTO dtos = new PropertyGetSpecificResponseDTO(
                property.getPropertyCode(),
                property.getPropertyType(),
                property.getPropertyStatus(),
                property.getPurpose(),
                property.getPropertyDescription(),
                property.getArea(),
                property.getPrice(),
                property.getPromotionalPrice(),
                property.getHighlight(),
                property.getFloors(),
                modelMapper.map(property.getTaxes(), TaxesPutRequestDTO.class),
                modelMapper.map(property.getAddress(), AddressGetResponseDTO.class),
                modelMapper.map(property.getPropertyFeatures(), PropertyFeatureSpecifiGetRespondeDTO.class),
                property.getAdditionals()
                        .stream()
                        .map(additional -> new AdditionalsGetResponseDTO(additional.getName()))
                        .toList(),
                realtors,
                proprietorDto,
                imagesString
        );
        return dtos;
    }

    public List<PropertyMapGetResponseDTO> findAllByFilterMap() {


        List<Property> allProperties = repository.findAll();

        List<PropertyMapGetResponseDTO> responseList = allProperties.stream()
                .map(propertyx -> modelMapper.map(propertyx, PropertyMapGetResponseDTO.class))
                .collect(Collectors.toList());

        return responseList;

    }


    public Page<PropertyCardGetResponseDTO> findAllByFilterCard(PropertyFilterPostResponseDTO propertyDto, Pageable pageable) {

        Integer bedRoom = null;
        Integer bathRoom = null;
        Integer garageSpace = null;
        Integer suite = null;

        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getBedRoom(), 5)) {

            bedRoom = propertyDto.getPropertyFeatures().getBedRoom();
            propertyDto.getPropertyFeatures().setBedRoom(null);

        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getBathRoom(), 5)) {
            bathRoom = propertyDto.getPropertyFeatures().getBathRoom();
            propertyDto.getPropertyFeatures().setBathRoom(null);

        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getGarageSpace(), 5)) {


            garageSpace = propertyDto.getPropertyFeatures().getGarageSpace();
            propertyDto.getPropertyFeatures().setGarageSpace(null);

        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getSuite(), 5)) {
            suite = propertyDto.getPropertyFeatures().getSuite();
            propertyDto.getPropertyFeatures().setSuite(null);

        }

        Property property = modelMapper.map(propertyDto, Property.class);

        //criando o example matcher específico dos filtros do imóvel
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        //chamando o matcher
        Example<Property> example = Example.of(property, matcher);


        List<Property> allProperties = repository.findAll(example);

        List<Property> filteredAllProperties = allProperties.stream().filter(propertyPrice -> propertyPrice.getPrice() >= propertyDto.getMinPric() && propertyPrice.getPrice() <= propertyDto.getMaxPric()) // Compare prices
                .collect(Collectors.toList());

        if (propertyDto.getPropertyFeatures() != null) {
            propertyDto.getPropertyFeatures().setBedRoom(bedRoom);
            propertyDto.getPropertyFeatures().setBathRoom(bathRoom);
            propertyDto.getPropertyFeatures().setGarageSpace(garageSpace);
            propertyDto.getPropertyFeatures().setSuite(suite);
        }

        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getBedRoom(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getBedRoom() >= 5).collect(Collectors.toList());
        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getBathRoom(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getBathRoom() >= 5).collect(Collectors.toList());
        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getGarageSpace(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getGarageSpace() >= 5).collect(Collectors.toList());
        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getSuite(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getSuite() >= 5).collect(Collectors.toList());
        }


        //transforme o lista para page aqui

        int start = pageable.getPageNumber() * pageable.getPageSize(); // Calculando o índice de início
        int end = Math.min(start + pageable.getPageSize(), filteredAllProperties.size());


        List<Property> pageProperty = filteredAllProperties.subList(start, end);

        // Criando um novo Page a partir da lista filtrada
        Page<Property> propertyFinal = new PageImpl<>(pageProperty, pageable, filteredAllProperties.size());

        //tranformando o page propery pro page da dto
        Page<PropertyCardGetResponseDTO> PropertyCardGetResponseDTO = propertyFinal.map(propertyx -> modelMapper.map(propertyx, PropertyCardGetResponseDTO.class));
        for (int i = 0; i < propertyFinal.getContent().size(); i++) {
            for (int y = 0; y < pageProperty.get(i).getImageProperties().size(); y++) {
                if (pageProperty.get(i).getImageProperties().get(y).getMainImage()) {

                    String image = Base64.getEncoder().encodeToString(imageService.getMainPropertyImage(pageProperty.get(i).getImageProperties().get(y).getId()));

                    PropertyCardGetResponseDTO.getContent().get(i).setMainImage(image);

                }
            }

        }
        return PropertyCardGetResponseDTO;
    }

    public Page<PropertyListGetResponseDTO> findAllByFilter(PropertyFilterPostResponseDTO propertyDto, Pageable pageable) {

        Integer bedRoom = null;
        Integer bathRoom = null;
        Integer garageSpace = null;
        Integer suite = null;

        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getBedRoom(), 5)) {

            bedRoom = propertyDto.getPropertyFeatures().getBedRoom();
            propertyDto.getPropertyFeatures().setBedRoom(null);

        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getBathRoom(), 5)) {
            bathRoom = propertyDto.getPropertyFeatures().getBathRoom();
            propertyDto.getPropertyFeatures().setBathRoom(null);

        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getGarageSpace(), 5)) {


            garageSpace = propertyDto.getPropertyFeatures().getGarageSpace();
            propertyDto.getPropertyFeatures().setGarageSpace(null);

        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getSuite(), 5)) {
            suite = propertyDto.getPropertyFeatures().getSuite();
            propertyDto.getPropertyFeatures().setSuite(null);

        }


        Property property = modelMapper.map(propertyDto, Property.class);

        //criando o example matcher específico dos filtros do imóvel
        System.out.println(property.toString());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                //ignore propertyFeatures if they are null
                .withIgnorePaths("propertyFeatures");

        //chamando o matcher
        Example<Property> example = Example.of(property, matcher);


        List<Property> allProperties = repository.findAll(example);
        System.out.println(allProperties.size());
        //filtrando archived
        System.out.println("archived" + propertyDto.isArchived());

        if (propertyDto.isArchived()) {
            allProperties = allProperties.stream().filter(propertyx -> propertyx.isArchived()).collect(Collectors.toList());
        } else {
            allProperties = allProperties.stream().filter(propertyx -> !propertyx.isArchived()).collect(Collectors.toList());
        }

        List<Property> filteredAllProperties = allProperties.stream().filter(propertyPrice -> propertyPrice.getPrice() >= propertyDto.getMinPric() && propertyPrice.getPrice() <= propertyDto.getMaxPric()) // Compare prices
                .collect(Collectors.toList());

        if (propertyDto.getPropertyFeatures() != null) {
            propertyDto.getPropertyFeatures().setBedRoom(bedRoom);
            propertyDto.getPropertyFeatures().setBathRoom(bathRoom);
            propertyDto.getPropertyFeatures().setGarageSpace(garageSpace);
            propertyDto.getPropertyFeatures().setSuite(suite);
        }

        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getBedRoom(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getBedRoom() >= 5).collect(Collectors.toList());
        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getBathRoom(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getBathRoom() >= 5).collect(Collectors.toList());
        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getGarageSpace(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getGarageSpace() >= 5).collect(Collectors.toList());
        }
        if (propertyDto.getPropertyFeatures() != null && Objects.equals(propertyDto.getPropertyFeatures().getSuite(), 5)) {
            filteredAllProperties = filteredAllProperties.stream().filter(propertyRoom -> propertyRoom.getPropertyFeatures().getSuite() >= 5).collect(Collectors.toList());
        }


        //transforme o lista para page aqui

        int start = pageable.getPageNumber() * pageable.getPageSize(); // Calculando o índice de início
        int end = Math.min(start + pageable.getPageSize(), filteredAllProperties.size());


        List<Property> pageProperty = filteredAllProperties.subList(start, end);

        // Criando um novo Page a partir da lista filtrada
        Page<Property> propertyFinal = new PageImpl<>(pageProperty, pageable, filteredAllProperties.size());

        //tranformando o page propery pro page da dto
        Page<PropertyListGetResponseDTO> propertyListGetResponseDTOS = propertyFinal.map(propertyx -> modelMapper.map(propertyx, PropertyListGetResponseDTO.class));

        return propertyListGetResponseDTOS;
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
            List<MultipartFile> newImages) {

        Property property = repository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Propriedade não encontrada"));

        property.setTitle(propertyDTO.getTitle());
        property.setPropertyDescription(propertyDTO.getPropertyDescription());
        property.setPropertyType(propertyDTO.getPropertyType());
        property.setPurpose(propertyDTO.getPurpose());
        property.setPropertyStatus(propertyDTO.getPropertyStatus());
        property.setPrice(propertyDTO.getPrice());
        property.setArea(propertyDTO.getArea());
        property.setPromotionalPrice(propertyDTO.getPromotionalPrice());
        property.setHighlight(propertyDTO.getHighlight());
//        property.setPropertyCategory(propertyDTO.getPropertyCategory());
        property.setFloors(propertyDTO.getFloors());

        modelMapper.map(propertyDTO.getPropertyFeatures(), property.getPropertyFeatures());
        modelMapper.map(propertyDTO.getTaxes(), property.getTaxes());
        modelMapper.map(propertyDTO.getAddress(), property.getAddress());
        updateRealtors(property, propertyDTO.getRealtors());
        updateProprietor(property, propertyDTO.getProprietor());
        updateAdditionals(property, propertyDTO.getAdditionals());


        repository.save(property);
//        vai ter que add isso
        processImages(propertyId, newImages);

        return property;
    }

    private void updateRealtors(Property property, List<Integer> realtorIds) {
        if (realtorIds != null) {
            List<Realtor> realtors = realtorService.findAllById(realtorIds);
            property.setRealtors(new ArrayList<>(realtors));
        }
    }

    private void updateProprietor(Property property, Integer proprietorId) {
        if (!property.getProprietor().getId().equals(proprietorId)) {
            property.setProprietor(proprietorService.findById(proprietorId));
        }
    }

    private void updateAdditionals(Property property, List<Integer> additionalIds) {
        if (additionalIds != null && !additionalIds.isEmpty()) {
            List<Additionals> additionals = additionalsService.findAllById(additionalIds);
            property.setAdditionals(new ArrayList<>(additionals));
        }
    }

    private void processImages(Integer propertyId, List<MultipartFile> newImages) {
        if (newImages != null && !newImages.isEmpty()) {
            imageService.deletePropertyImages(propertyId);
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

        Property property = repository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Property not found"));

        ProprietorPropertyDataExtra proprietorExtra = null;
        if (property.getProprietor() != null) {
            proprietorExtra = new ProprietorPropertyDataExtra(
                    property.getProprietor().getName(),
                    property.getProprietor().getCpf()
            );
        }

        List<RealtorsPropertyDataExtra> realtorExtras = property.getRealtors() != null
                ? property.getRealtors().stream()
                .map(r -> new RealtorsPropertyDataExtra(r.getName(), r.getCpf()))
                .toList()
                : List.of();

        List<Integer> idImages = new ArrayList<>();

        if (property.getImageProperties() != null && !property.getImageProperties().isEmpty()) {
            for (ImageProperty images : property.getImageProperties()) {
                idImages.add(images.getId());
            }
        }

        return PropertyPutRequestDTO.builder()
                .title(property.getTitle())
                .propertyDescription(property.getPropertyDescription())
                .propertyType(property.getPropertyType())
                .purpose(property.getPurpose())
                .propertyStatus(property.getPropertyStatus())
                .area(property.getArea())
                .price(property.getPrice())
                .promotionalPrice(property.getPromotionalPrice())
                .highlight(property.getHighlight())
                .floors(property.getFloors())
                .propertyFeatures(new PropertyFeaturePutRequestDTO() {{
                    setAllowsPet(property.getPropertyFeatures().getAllowsPet());
                    setBedRoom(property.getPropertyFeatures().getBedRoom());
                    setLivingRoom(property.getPropertyFeatures().getLivingRoom());
                    setSuite(property.getPropertyFeatures().getSuite());
                    setBathRoom(property.getPropertyFeatures().getBathRoom());
                    setGarageSpace(property.getPropertyFeatures().getGarageSpace());
                    setFurnished(property.getPropertyFeatures().getIsFurnished());
                }})
                .taxes(new TaxesPutRequestDTO() {{
                    setCondominiumFee(property.getTaxes().getCondominiumFee());
                    setIptu(property.getTaxes().getIptu());
                }})
                .address(new AddressPostRequestDTO(
                        property.getAddress().getCep(),
                        property.getAddress().getStreet(),
                        property.getAddress().getNeighborhood(),
                        property.getAddress().getCity(),
                        property.getAddress().getState(),
                        property.getAddress().getPropertyNumber(),
                        property.getAddress().getComplement()
                ))
                .proprietor(property.getProprietor() != null ? property.getProprietor().getId() : null)
                .realtors(property.getRealtors() != null
                        ? property.getRealtors().stream().map(Realtor::getId).toList()
                        : List.of())
                .additionals(property.getAdditionals() != null
                        ? property.getAdditionals().stream().map(Additionals::getId).toList()
                        : List.of())
                .proprietorExtraData(proprietorExtra)
                .proprietorExtraData(proprietorExtra)
                .realtorsExtraData(realtorExtras)
                .imageIds(idImages)
                .build();
    }

    public Page<PropertyCardGetResponseDTO> findPropertyCard(Pageable pageable) {
        Page<Property> properties = repository.findAll(pageable);
        List<PropertyCardGetResponseDTO> dtos = properties.getContent().stream().map(property -> new PropertyCardGetResponseDTO(
                modelMapper.map(property.getPropertyFeatures(), PropertyFeatureCardGetResponseDTO.class),
                modelMapper.map(property.getAddress(), AddressCardGetResponseDTO.class), property.getPrice(),
                property.getPurpose(),
                property.getPropertyStatus(),
                property.getPromotionalPrice(),
                property.getId(),
                property.getPropertyType(),
                property.getArea(),
                null

        )).toList();
        return new PageImpl<>(dtos, pageable, properties.getTotalElements());
    }

    public List<RealtorGetResponseDTOwithId> findRealtorsByPropertyId(Integer id) {
        Property property = repository.findById(id).orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        List<Realtor> realtors = property.getRealtors();

        return realtors.stream().filter(realtor -> {
            List<Schedules> schedules = schedulesService.findAllByRealtorIdAndFuture(realtor.getId());
            // Verifica se há ao menos um horário livre (sem propriedade e cliente)
            return schedules.stream().anyMatch(schedule -> schedule.getProperty() == null && schedule.getCustomer() == null);
        }).map(realtor -> modelMapper.map(realtor, RealtorGetResponseDTOwithId.class)).collect(Collectors.toList());
    }

    //    public List<PropertyCardGetResponseDTO> findRandomHighlights() {
//        return repository.findRandomHighlighted5().stream()
//                .map(sch -> modelMapper.map(sch, PropertyCardGetResponseDTO.class)).toList();
//    }
    public List<PropertyCardGetResponseDTO> findRandomHighlights() {

        return repository.findRandomHighlighted5().stream()
                .filter(property -> !property.isArchived()) // Filtra apenas propriedades não arquivadas

                .map(property -> {
                    PropertyCardGetResponseDTO dto = modelMapper.map(property, PropertyCardGetResponseDTO.class);

                    // Buscar a imagem principal e setar o ID
                    if (property.getImageProperties() != null && !property.getImageProperties().isEmpty()) {
                        property.getImageProperties().stream()
                                .filter(ImageProperty::getMainImage)
                                .findFirst()
                                .ifPresent(img -> {


                                    byte[] imageBytes = imageService.getMainPropertyImage(img.getId());
                                    if (imageBytes != null) {
                                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                                        dto.setMainImage(base64Image); // Supondo que seu DTO tenha esse setter
                                    }
                                });
                    }


                    return dto;
                })
                .toList();
    }

    public Long getAllRegistredNumber() {
        return repository.count();
    }

    public double getPercentageOfRentalProperties() {
        List<Property> allProperties = repository.findAll();
        if (allProperties.isEmpty()) {
            return 0.0;
        }

        long rentalCount = allProperties.stream()
                .filter(property -> "locacao".equals(property.getPurpose()))
                .count();
        return (rentalCount * 100.0) / allProperties.size();
    }

    public double getPercentageOfForSaleProperties() {
        List<Property> allProperties = repository.findAll();
        if (allProperties.isEmpty()) {
            return 0.0;
        }

        long forSaleCount = allProperties
                .stream()
                .filter(property -> "venda".equals(property.getPurpose()))
                .count();
        return (forSaleCount * 100.0) / allProperties.size();
    }

    public double getPercentageOfArchiveStatus() {
        List<Property> allProperties = repository.findAll();
        if (allProperties.isEmpty()) {
            return 0.0;
        }

        long archiveStatus = allProperties
                .stream()
                .filter(Property::isArchived)
                .count();
        return (archiveStatus * 100.0) / allProperties.size();
    }

    public long getQuantityOfRentalProperties() {
        return repository.findAll()
                .stream()
                .filter(property -> "locacao".equals(property.getPurpose()))
                .count();
    }

    public long getQuantityOfForSaleProperties() {
        return repository.findAll()
                .stream()
                .filter(property -> "venda".equals(property.getPurpose()))
                .count();
    }

    public long getQuantityOfArchivedProperties() {
        return repository.findAll()
                .stream()
                .filter(Property::isArchived)
                .count();
    }

    public List<PropertyCardGetResponseDTO> similarProperties() {
        List<Property> properties = repository.findMostRecentSellProperties("VENDA");

        List<PropertyCardGetResponseDTO> list = properties.stream()
                .map(property -> modelMapper.map(property, PropertyCardGetResponseDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < properties.size(); i++) {
            for (ImageProperty imageProperty : properties.get(i).getImageProperties()) {
                if (Boolean.TRUE.equals(imageProperty.getMainImage())) { // Verifica se é a imagem principal
                    try {
                        byte[] imageBytes = imageService.getMainPropertyImage(imageProperty.getId());
                        if (imageBytes != null) {
                            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                            list.get(i).setMainImage(base64Image);
                            break; // Sai do loop após encontrar a imagem principal
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar imagem principal da propriedade: " + e.getMessage());
                    }
                }
            }
        }

        return list;
    }

    public List<PropertyCardGetResponseDTO> findMostRecentSellProperties() {
        List<Property> properties = repository.findMostRecentSellProperties("VENDA");

        List<PropertyCardGetResponseDTO> list = properties.stream()
                .map(property -> modelMapper.map(property, PropertyCardGetResponseDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < properties.size(); i++) {
            for (ImageProperty imageProperty : properties.get(i).getImageProperties()) {
                if (Boolean.TRUE.equals(imageProperty.getMainImage())) { // Verifica se é a imagem principal
                    try {
                        byte[] imageBytes = imageService.getMainPropertyImage(imageProperty.getId());
                        if (imageBytes != null) {
                            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                            list.get(i).setMainImage(base64Image);
                            break; // Sai do loop após encontrar a imagem principal
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar imagem principal da propriedade: " + e.getMessage());
                    }
                }
            }
        }

        return list;
    }

    public List<PropertyCardGetResponseDTO> findMostRecentLeaseProperties() {
        List<Property> properties = repository.findMostRecentLeaseProperties("LOCACAO");

        List<PropertyCardGetResponseDTO> list = properties.stream()
                .map(property -> modelMapper.map(property, PropertyCardGetResponseDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < properties.size(); i++) {
            for (ImageProperty imageProperty : properties.get(i).getImageProperties()) {
                if (Boolean.TRUE.equals(imageProperty.getMainImage())) { // Verifica se é a imagem principal
                    try {
                        byte[] imageBytes = imageService.getMainPropertyImage(imageProperty.getId());
                        if (imageBytes != null) {
                            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                            list.get(i).setMainImage(base64Image);
                            break; // Sai do loop após encontrar a imagem principal
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar imagem principal da propriedade: " + e.getMessage());
                    }
                }
            }
        }

        return list;
    }

    //Sem filtro
//    public List<PropertyCardGetResponseDTO> findRandomHighlighted9() {
//        return repository.findRandomHighlighted9().stream().map(sch -> modelMapper.map(sch, PropertyCardGetResponseDTO.class)).toList();
//    }

    public List<PropertyCardGetResponseDTO> findRandomHighlighted9Sale() {
        List<Property> promotionalProperties = repository.findRandomPromotional9ForSale();

        List<PropertyCardGetResponseDTO> list = promotionalProperties.stream()
                .map(property -> modelMapper.map(property, PropertyCardGetResponseDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < promotionalProperties.size(); i++) {
            for (ImageProperty imageProperty : promotionalProperties.get(i).getImageProperties()) {
                if (imageProperty.getMainImage()) {  // Verifica se é a imagem principal
                    try {
                        byte[] imageBytes = imageService.getMainPropertyImage(imageProperty.getId());
                        if (imageBytes != null) {
                            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                            list.get(i).setMainImage(base64Image);
                            break;  // Sai do loop após encontrar a imagem principal
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar imagem principal da propriedade {}");
                    }
                }
            }


        }
        return list;

    }

    public List<PropertyCardGetResponseDTO> findRandomHighlighted9Lease() {
        List<Property> promotionalProperties = repository.findRandomPromotional9ForLease();

        List<PropertyCardGetResponseDTO> list = promotionalProperties.stream()
                .map(property -> modelMapper.map(property, PropertyCardGetResponseDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < promotionalProperties.size(); i++) {
            for (ImageProperty imageProperty : promotionalProperties.get(i).getImageProperties()) {
                if (imageProperty.getMainImage()) {  // Verifica se é a imagem principal
                    try {
                        byte[] imageBytes = imageService.getMainPropertyImage(imageProperty.getId());
                        if (imageBytes != null) {
                            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                            list.get(i).setMainImage(base64Image);
                            break;  // Sai do loop após encontrar a imagem principal
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar imagem principal da propriedade {}");
                    }
                }
            }
        }


        return list;
    }
}

