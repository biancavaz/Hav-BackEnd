package com.hav.hav_imobiliaria.security.controllerSecurity;

import com.hav.hav_imobiliaria.security.modelSecurity.Message;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RealtimeChat {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    @SendTo("/group/public")
    public Message receiveMessage(@Payload Message message) {

        messagingTemplate.convertAndSend(
                "/group/" + message.getChat().getId().toString(), message);

        return message;
    }
}
//@RestController
//@AllArgsConstructor
//public class RealtimeChat {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @MessageMapping("/message")
//    public void receiveMessage(@Payload Message message) {
//        messagingTemplate.convertAndSend(
//                "/group/" + message.getChat().getId().toString(),
//                message
//        );
//    }
//}
