package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.PropertyRepository;
import com.hav.hav_imobiliaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FavoritesService {

    private UserRepository userRepository;
    private PropertyRepository propertyRepository;

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


}
