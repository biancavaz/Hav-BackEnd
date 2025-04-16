package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Users.UserConfigurationDto;
import com.hav.hav_imobiliaria.model.DTO.Users.UserConfigurationDtoEdit;
import com.hav.hav_imobiliaria.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public Long getAllRegistredNumber(){
        return userService.getAllRegistredNumber();
    }
    @GetMapping("/getPercentageProprietors")
    public Long getPercentageProprietors() {
        return userService.getPercentageProprietors();
    }

    @GetMapping("/getQuantityCustomer")
    public Long getQuantityCustomer() {
        return userService.getQuantityClients();
    }

    @GetMapping("/getPercentageClients")
    public Long getPercentageClients() {
        return userService.getPercentageClients();
    }

    @GetMapping("/configuration")
    public UserConfigurationDto getUser(@CookieValue("token") String token){
        return userService.findUserByJwt(token);
    }
    @PutMapping("/configuration")
    public void editUser(@CookieValue("token") String token, @RequestBody UserConfigurationDtoEdit userConfigurationDtoEdit){
        System.out.println(userConfigurationDtoEdit);
        System.out.println("------------------  ");
        userService.editUserByJwt(token, userConfigurationDtoEdit);
    }
}
