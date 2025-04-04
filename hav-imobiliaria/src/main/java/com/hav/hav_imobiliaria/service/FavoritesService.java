package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeatureCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import com.hav.hav_imobiliaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FavoritesService {

    private UserRepository userRepository;
    private PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;

    public void favoritar(Integer idProperty, Integer idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Property property = propertyRepository.findById(idProperty).orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        if (user.getProperties() == null) {
            user.setProperties(new ArrayList<>());
        }
        if (property.getUsers() == null) {
            property.setUsers(new ArrayList<>());
        }

        if (!user.getProperties().contains(property)) {
            user.getProperties().add(property);
            property.getUsers().add(user);

            userRepository.save(user);
            propertyRepository.save(property);
        }
    }

    public void desfavoritar(Integer idProperty, Integer idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Property property = propertyRepository.findById(idProperty).orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        if (user.getProperties().contains(property)) {
            user.getProperties().remove(property);
            property.getUsers().remove(user);

            userRepository.save(user);
            propertyRepository.save(property);
        }
    }

    public Page<PropertyCardGetResponseDTO> returnFavorites(Pageable pageable, Integer idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Property> favoriteProperties = user.getProperties();

        List<PropertyCardGetResponseDTO> dtos = favoriteProperties.stream().map(property -> new PropertyCardGetResponseDTO(
                modelMapper.map(property.getPropertyFeatures(), PropertyFeatureCardGetResponseDTO.class),
                modelMapper.map(property.getAddress(), AddressCardGetResponseDTO.class), property.getPrice(),
                property.getPurpose(),
                property.getPropertyStatus(),
                property.getPromotionalPrice(),
                property.getId(),
                property.getPropertyType(),
                property.getArea()

        )).toList();
        return new PageImpl<>(dtos, pageable, idUser);
    }

}
