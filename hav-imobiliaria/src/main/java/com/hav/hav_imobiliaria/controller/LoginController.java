package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Login.LoginDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO){

        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());


        auth = authManager.authenticate(auth);

        System.out.println(auth);

        if(auth.isAuthenticated()){
            return "login feito";
        }
        return "login não feito";
    }

}
