package com.hav.hav_imobiliaria.security.controllerSecurity;

import com.hav.hav_imobiliaria.security.ResponseSecurity.ApiResponse;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.ChatException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.MessageException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Message;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.SendMessageRequest;
import com.hav.hav_imobiliaria.security.serviceSecurity.MessageService;
import com.hav.hav_imobiliaria.security.serviceSecurity.UserSecurityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserSecurityService userSecurityService;

    @PostMapping("/create")
    public Message sendMessageHandler(
            @RequestBody SendMessageRequest req,
            @CookieValue("token") String jwt) throws ChatException, UserException {

        UserSecurity user = userSecurityService.findUserProfile(jwt);

        req.setUserId(user.getId());

        return messageService.sendMessage(req);
    }

    @GetMapping("/chat/{chatId}")
    public List<Message> getChatsMessagesHandler(
            @PathVariable Integer chatId,
            @CookieValue("token") String jwt)
            throws ChatException, UserException {

        UserSecurity user = userSecurityService.findUserProfile(jwt);

        return messageService.getChatsMessages(chatId, user);
    }

    @DeleteMapping("/{messageId}")
    public ApiResponse deleteMessagesByIdHandler(
            @PathVariable Integer messageId,
            @CookieValue("token") String jwt)
            throws UserException, MessageException {

        UserSecurity user = userSecurityService.findUserProfile(jwt);

        messageService.deleteMessageById(messageId, user);

        return new ApiResponse("Message deleted successfully", false);
    }
}
