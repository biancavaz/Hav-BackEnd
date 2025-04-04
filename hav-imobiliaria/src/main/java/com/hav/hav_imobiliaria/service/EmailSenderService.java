package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
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
    private CustumerRepository custumerRepository;

    public void sendEmailContactUs(Integer id,  String subject, String body ) {
        Customer customer = custumerRepository.findById(id).get();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("hav.suporte@gmail.com");
        message.setSubject(subject);
        message.setText("Enviado por "+customer.getEmail()+"\n\n"+body);
        System.out.println(message);
        mailSender.send(message);
    }

}
