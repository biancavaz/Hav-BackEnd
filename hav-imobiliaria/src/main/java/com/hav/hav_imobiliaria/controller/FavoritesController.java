package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.DTO.Property.PropertyMapGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.service.FavoritesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequestMapping("/favorites")
@RestController
public class    FavoritesController {

    private final FavoritesService favoritesService;

    @PostMapping("/favoritar/{idProperty}")
    @ResponseStatus(HttpStatus.CREATED)
    public void favoritar(@CookieValue("token") String token, @PathVariable Integer idProperty) {
        favoritesService.favoritar(idProperty, token);
    }

    @GetMapping("/map")
    public List<PropertyMapGetResponseDTO> findByFilterMapFavorite(@CookieValue("token") String token) {
        return favoritesService.findAllByFilterMapFavorite(token);
    }

    @DeleteMapping("/desfavoritar/{idProperty}")
    @ResponseStatus(HttpStatus.OK)
    public void desfavoritar(@CookieValue("token") String token, @PathVariable Integer idProperty) {
        favoritesService.desfavoritar(idProperty, token);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PropertyCardGetResponseDTO> returnFavorites(
            @CookieValue("token") String token) {

        return favoritesService.returnFavorites(token);
    }

    //funcionando
    @GetMapping("/isFavorited/{idProperty}")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isFavorited(@CookieValue("token") String token, @PathVariable Integer idProperty) {
        return favoritesService.isFavorited(idProperty, token);
    }

}
