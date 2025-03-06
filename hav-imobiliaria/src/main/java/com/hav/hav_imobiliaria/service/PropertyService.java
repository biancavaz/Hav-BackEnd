package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPostRequestDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyPutRequestDTO;
import com.hav.hav_imobiliaria.model.entity.Additionals;
import com.hav.hav_imobiliaria.model.entity.CustomerOwner;
import com.hav.hav_imobiliaria.model.entity.Property;
import com.hav.hav_imobiliaria.model.entity.Realtor;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
                        property.getOwner() != null ? property.getOwner().getName() : ""
                ))
                .toList();

        // Retorna uma nova Page contendo os DTOs
        return new PageImpl<>(dtos, pageable, properties.getTotalElements());
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
