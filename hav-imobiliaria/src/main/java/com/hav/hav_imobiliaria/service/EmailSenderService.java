package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyCardGetResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to,  String subject, String body ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);

        message.setSubject(subject);
        message.setText(body);
        System.out.println(message);
        mailSender.send(message);
    }
}
