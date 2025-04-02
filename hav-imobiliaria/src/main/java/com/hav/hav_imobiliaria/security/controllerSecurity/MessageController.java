package com.hav.hav_imobiliaria.security.controllerSecurity;

import com.hav.hav_imobiliaria.security.exceptionsSecurity.ChatException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Message;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.SendMessageRequest;
import com.hav.hav_imobiliaria.security.serviceSecurity.MessageService;
import com.hav.hav_imobiliaria.security.serviceSecurity.UserSecurityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserSecurityService userSecurityService;

    public Message sendMessageHandler(
            @RequestBody SendMessageRequest req,
            @RequestHeader("Authorization") String jwt) throws ChatException, UserException {

        UserSecurity user = userSecurityService.findUserProfile(jwt);

        req.setUserId(user.getId());

        return messageService.sendMessage(req);
    }
}
