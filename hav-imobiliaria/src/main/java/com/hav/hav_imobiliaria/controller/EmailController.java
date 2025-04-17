package com.hav.hav_imobiliaria.controller;

import com.hav.hav_imobiliaria.model.DTO.Email.EmailComplainDTO;
import com.hav.hav_imobiliaria.security.configSecurity.JwtConstant;
import com.hav.hav_imobiliaria.security.configSecurity.JwtTokenValidator;
import com.hav.hav_imobiliaria.security.configSecurity.TokenProvider;
import com.hav.hav_imobiliaria.service.EmailSenderService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
@AllArgsConstructor
public class EmailController {

    EmailSenderService emailSenderService;
    TokenProvider tokenProvider;

    @PostMapping("/contactus")
    public String sendContactUsEmail(@RequestBody EmailComplainDTO email, @CookieValue("token") String token){
        send(token, email.getSubject(), email.getBody());
        return "email sent";
    };

    @Async
    protected void send(String jwt, String subject, String body) {
        String email = tokenProvider.getEmailFromToken(jwt);
        try {
            emailSenderService.sendEmailContactUs(email, subject, body);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("failed to send email");
        }
    }
}
