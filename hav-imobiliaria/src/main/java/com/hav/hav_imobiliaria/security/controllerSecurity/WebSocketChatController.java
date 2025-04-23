package com.hav.hav_imobiliaria.security.controllerSecurity;

import com.hav.hav_imobiliaria.security.exceptionsSecurity.ChatException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Message;
import com.hav.hav_imobiliaria.security.requestSecurity.SendMessageRequest;
import com.hav.hav_imobiliaria.security.serviceSecurity.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class WebSocketChatController {

    private final MessageService messageService; // serviço onde salva a msg e busca dados se necessário
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    public void sendMessage(@Payload SendMessageRequest messageDTO) throws ChatException, UserException {
        Message savedMessage = messageService.sendMessage(messageDTO); // salva no banco
        messagingTemplate.convertAndSend(
                "/topic/chat/" + messageDTO.getChatId(), // envia para os usuários do chat certo
                savedMessage
        );
    }
}
