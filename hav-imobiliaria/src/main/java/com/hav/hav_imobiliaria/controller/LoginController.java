package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Login.LoginDTO;
import com.hav.hav_imobiliaria.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO,
                                        HttpServletResponse response) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        try {
            auth = authManager.authenticate(auth);

            if (auth.isAuthenticated()) {

                String jwt = jwtService.generateToken(auth.getName());

                ResponseCookie cookie = ResponseCookie.from("token", jwt)
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .maxAge(Duration.ofDays(1))
                        .sameSite("Strict")
                        .build();

                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

                System.out.println("Login successful");
                return ResponseEntity.ok("Login feito com sucesso");
            } else {
                System.out.println("Authentication failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Autenticação Falhou");
            }

        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais Inválidas");
        }
    }

}
