package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Address.AddressCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyMapGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.PropertyFeature.PropertyFeatureCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import com.hav.hav_imobiliaria.repository.UserRepository;
import com.hav.hav_imobiliaria.security.configSecurity.JwtTokenValidator;
import com.hav.hav_imobiliaria.security.configSecurity.TokenProvider;
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
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FavoritesService {

    private UserRepository userRepository;
    private PropertyRepository propertyRepository;
    private final ModelMapper modelMapper;
    private final TokenProvider tokenProvider;
    public void favoritar(Integer idProperty, String jwt) {

        User user = userRepository.findByEmail(tokenProvider.getEmailFromToken(jwt)).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
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

    public void desfavoritar(Integer idProperty, String jwt) {
        User user = userRepository.findByEmail(tokenProvider.getEmailFromToken(jwt)).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Property property = propertyRepository.findById(idProperty).orElseThrow(() -> new RuntimeException("Propriedade não encontrada"));

        if (user.getProperties().contains(property)) {
            user.getProperties().remove(property);
            property.getUsers().remove(user);

            userRepository.save(user);
            propertyRepository.save(property);
        }
    }


    public List<PropertyMapGetResponseDTO> findAllByFilterMapFavorite(String jwt){

        User user = userRepository.findByEmail(tokenProvider.getEmailFromToken(jwt)).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Property> allProperties = user.getProperties();


        List<PropertyMapGetResponseDTO> responseList = allProperties.stream()
                .map(propertyx -> modelMapper.map(propertyx, PropertyMapGetResponseDTO.class))
                .collect(Collectors.toList());
        return responseList;



    }

    public Page<PropertyCardGetResponseDTO> returnFavorites(Pageable pageable, String jwt) {
        User user = userRepository.findByEmail(tokenProvider.getEmailFromToken(jwt)).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

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

        int start = pageable.getPageNumber() * pageable.getPageSize(); // Calculando o índice de início
        int end = Math.min(start + pageable.getPageSize(), dtos.size());

        List<PropertyCardGetResponseDTO> pageProperty = dtos.subList(start, end);


        return new PageImpl<>(pageProperty, pageable, favoriteProperties.size());
    }

}
