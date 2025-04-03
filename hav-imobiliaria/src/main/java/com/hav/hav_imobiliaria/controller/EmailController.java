package com.hav.hav_imobiliaria.controller;

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
@RequestMapping("/contactus")
@AllArgsConstructor
public class EmailController {

    EmailSenderService emailSenderService;

    @PostMapping()
    public String sendContactUsEmail(@RequestBody String email){
        System.out.println(email);
        send(email,  "email formal", "algo assunto");
        return "sent";
    };

    @Async
    protected void send(String to, String email, String subject) {
        try {
            emailSenderService.sendEmail(to, subject, email);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("failed to send email");
        }
    }
}
