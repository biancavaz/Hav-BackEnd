package com.hav.hav_imobiliaria.service;

import com.hav.hav_imobiliaria.model.DTO.Property.PropertyCardGetResponseDTO;
import com.hav.hav_imobiliaria.model.entity.Users.Customer;
import com.hav.hav_imobiliaria.repository.CustumerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
@AllArgsConstructor
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    private CustumerRepository custumerRepository;

    public void sendEmail(Integer id, String subject, String body) {
        Customer customer = custumerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente com ID " + id + " não encontrado."));


        try {
            MimeMessage message = mailSender.createMimeMessage();

            // true = multipart (permite anexos, HTML etc.)
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo("hav.suporte@gmail.com");
            helper.setSubject(subject);

            String content = "<p><strong>Email enviado por:</strong> " + customer.getEmail() + "</p>"
                    + "<p>" + body + "</p>";

            helper.setText(content, true); // true = interpretar como HTML

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            // Aqui você pode lançar uma exceção personalizada ou logar melhor o erro
        }
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
