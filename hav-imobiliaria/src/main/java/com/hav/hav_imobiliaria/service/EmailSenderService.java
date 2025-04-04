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
        //isso aqui talvez troque para o context holder do security
        Customer customer = custumerRepository.findById(id).get();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("hav.suporte@gmail.com");
        message.setSubject(subject);
        message.setText("Enviado por "+customer.getEmail()+"\n\n"+body);
        mailSender.send(message);
    }

    public void sendPasswordNewAccount(String email,  String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Conta criada");
        message.setText("Sua conta foi criada\nLogin: "+email+"\nSenha: "+password+
                "\n\nÉ RECOMENDADO MUDAR ESTA SENHA NO PRIMEIRO ACESSO.");
        mailSender.send(message);
    }
    public void sendPasswordNewAccount(String email,  String password, String type) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Conta criada");
        message.setText("Sua conta de "+type+" foi criada\nLogin: "+email+"\nSenha: "+password+
                "\n\nÉ RECOMENDADO MUDAR ESTA SENHA NO PRIMEIRO ACESSO.");
        mailSender.send(message);
    }


}
