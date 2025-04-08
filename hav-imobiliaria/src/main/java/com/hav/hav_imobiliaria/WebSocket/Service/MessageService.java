package com.hav.hav_imobiliaria.WebSocket.Service;

import com.hav.hav_imobiliaria.WebSocket.Notification.NotificationDTO;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserRepository userRepository;

    public void enviarMensagemWebSocket(NotificationDTO mensagem) {
        User sender = userRepository.findById(mensagem.getSender()).orElseThrow(() ->
                new RuntimeException("Erro ao enviar mensagem"));
        User recipient = userRepository.findById(mensagem.getRecipient()).orElseThrow(() ->
                new RuntimeException("Erro ao receber mensagem"));
        mensagem.setSenderName(sender.getName());
        mensagem.setContent(mensagem.getContent());
        simpMessagingTemplate.convertAndSend("/topic/mensagens"+recipient.getId(),mensagem);
    }
}
