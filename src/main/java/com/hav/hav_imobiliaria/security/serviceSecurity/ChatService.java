package com.hav.hav_imobiliaria.security.serviceSecurity;

import com.hav.hav_imobiliaria.security.exceptionsSecurity.ChatException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Chat;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.GroupChatRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChatService {

    Chat createChat(UserSecurity reqUser, Integer userId2) throws UserException, ChatException;

    Optional<Chat> findChatById(Integer chatId) throws ChatException;

    List<Chat> findChatsByUserId(Integer userId) throws UserException;

    Chat createGroupChat(GroupChatRequest req, UserSecurity reqUser) throws UserException;

    Chat addUserToGroupChat(Integer userId, Integer chatId, UserSecurity reqUser) throws UserException, ChatException;

    Chat removeUserFromGroupChat(Integer chatId, Integer userId, UserSecurity reqUser) throws UserException, ChatException;

    Chat renameGroup(Integer chatId, String groupName, UserSecurity reqUser) throws UserException, ChatException;

    void deleteChat(Integer chatId, Integer userId) throws ChatException;

    Optional<Chat> findChatByUsersId(Integer userId1, Integer userId2) throws ChatException, UserException;
}
