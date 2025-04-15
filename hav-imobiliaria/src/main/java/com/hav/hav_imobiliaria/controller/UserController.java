package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Users.UserConfigurationDto;
import com.hav.hav_imobiliaria.repository.UserRepository;
import com.hav.hav_imobiliaria.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/configuration")
    public UserConfigurationDto getUser(@RequestHeader("Authorization") String authorizationHeader){
        return userService.findUserByJwt(authorizationHeader);
    }
}

