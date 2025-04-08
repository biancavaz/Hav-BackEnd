package com.hav.hav_imobiliaria.WebSocket.Controller;

import com.hav.hav_imobiliaria.WebSocket.Notification.NotificationDTO;
import com.hav.hav_imobiliaria.WebSocket.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mensagem")
public class MessageController {

    @Autowired
    private MessageService mensagemService;

    @PostMapping
    public void enviarMensagem(@RequestBody NotificationDTO mensagem) {
        mensagemService.enviarMensagemWebSocket(mensagem);
        System.out.println(mensagem.getContent());
    }
}
