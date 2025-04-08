package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyCardGetResponseDTO;
import com.hav.hav_imobiliaria.service.FavoritesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/favorites")
@RestController
public class FavoritesController {

    private final FavoritesService favoritesService;

    @PostMapping("/favoritar/{idUser}/{idProperty}")
    @ResponseStatus(HttpStatus.CREATED)
    public void favoritar(@PathVariable Integer idUser, @PathVariable Integer idProperty) {
        favoritesService.favoritar(idProperty, idUser);
    }

    @DeleteMapping("/desfavoritar/{idUser}/{idProperty}")
    @ResponseStatus(HttpStatus.OK)
    public void desfavoritar(@PathVariable Integer idUser, @PathVariable Integer idProperty) {
        favoritesService.desfavoritar(idProperty, idUser);
    }

    @GetMapping("/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    public Page<PropertyCardGetResponseDTO> returnFavorites(
            @PathVariable Integer idUser,
            Pageable pageable) {
        return favoritesService.returnFavorites(pageable, idUser);
    }
}
