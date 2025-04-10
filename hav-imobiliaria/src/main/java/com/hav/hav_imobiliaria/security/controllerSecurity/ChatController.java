package com.hav.hav_imobiliaria.security.controllerSecurity;

import com.hav.hav_imobiliaria.security.ResponseSecurity.ApiResponse;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.ChatException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Chat;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.GroupChatRequest;
import com.hav.hav_imobiliaria.security.requestSecurity.SingleChatRequest;
import com.hav.hav_imobiliaria.security.serviceSecurity.ChatService;
import com.hav.hav_imobiliaria.security.serviceSecurity.UserSecurityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chats")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserSecurityService userSecurityService;

    @PostMapping("/single")
    @ResponseStatus(HttpStatus.CREATED)
    public Chat createChatHandler(
            @Valid @RequestBody SingleChatRequest singleChatRequest,
            @RequestHeader("Authorization") String jwt
    ) throws UserException, ChatException {

        UserSecurity reqUser = userSecurityService.findUserProfile(jwt);

        return chatService.createChat(reqUser, singleChatRequest.getUserId());
    }

    @PostMapping("/group")
    @ResponseStatus(HttpStatus.CREATED)
    public Chat createGroupHandler(
            @Valid @RequestBody GroupChatRequest groupChatRequest,
            @RequestHeader("Authorization") String jwt
    ) throws UserException {

        UserSecurity reqUser = userSecurityService.findUserProfile(jwt);

        return chatService.createGroupChat(groupChatRequest, reqUser);
    }

    @GetMapping("/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Chat> findChatByIdHandler(
            @Positive @PathVariable Integer chatId,
            @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        return chatService.findChatById(chatId);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<Chat> findAllChatByUserIdHandler(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {

        UserSecurity reqUser = userSecurityService.findUserProfile(jwt);

        return chatService.findChatsByUserId(reqUser.getId());
    }

    @PutMapping("/{chatId}/add/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Chat addUserToGroupHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer chatId,
            @PathVariable Integer userId) throws UserException, ChatException {

        UserSecurity reqUser = userSecurityService.findUserProfile(jwt);

        return chatService.addUserToGroupChat(userId, chatId, reqUser);
    }

    @PutMapping("/{chatId}/remove/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Chat removeUserFromGroupHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer chatId,
            @PathVariable Integer userId) throws UserException, ChatException {

        UserSecurity reqUser = userSecurityService.findUserProfile(jwt);

        return chatService.removeUserFromGroupChat(chatId, userId, reqUser);
    }

    @DeleteMapping("/delete/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteChatHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Integer chatId)
            throws UserException, ChatException {

        UserSecurity reqUser = userSecurityService.findUserProfile(jwt);

        chatService.deleteChat(chatId, reqUser.getId());

        return new ApiResponse("Chat was deleted successfully", false);
    }
}
