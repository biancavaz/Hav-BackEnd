package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
