package com.hav.hav_imobiliaria;

import com.hav.hav_imobiliaria.security.configSecurity.TokenProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@SpringBootApplication
public class HavImobiliariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HavImobiliariaApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(TokenProvider tokenProvider) {
        return args -> {
            Authentication authentication = new UsernamePasswordAuthenticationToken("a", "a", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
            String token = tokenProvider.generateToken(authentication);
            System.out.println("admin "+token);
            Authentication authentication1 = new UsernamePasswordAuthenticationToken("a", "a", List.of(new SimpleGrantedAuthority("ROLE_EDITOR")));
            String token1 = tokenProvider.generateToken(authentication1);
            System.out.println("editor "+token1);
            Authentication authentication2 = new UsernamePasswordAuthenticationToken("a", "a", List.of(new SimpleGrantedAuthority("ROLE_REALTOR")));
            String token2 = tokenProvider.generateToken(authentication2);
            System.out.println("realtor "+token2);
            Authentication authentication3 = new UsernamePasswordAuthenticationToken("a", "a", List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
            String token3 = tokenProvider.generateToken(authentication3);
            System.out.println("customer "+token3);
        };
    }
}
