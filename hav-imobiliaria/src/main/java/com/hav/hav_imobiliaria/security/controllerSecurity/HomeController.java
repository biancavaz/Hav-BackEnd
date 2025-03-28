package com.hav.hav_imobiliaria.security.controllerSecurity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hav")
public class HomeController {

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public String havImobliliaria() {
        return "Welcome to our chatHav!";
    }
}
