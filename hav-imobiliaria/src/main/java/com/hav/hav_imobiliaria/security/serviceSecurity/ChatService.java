package com.hav.hav_imobiliaria.security.serviceSecurity;

import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Chat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {

    public Chat createChat(Integer reqUserId, Integer userId2, boolean isGroup) throws UserException;

    public Chat findChatById(Integer chatId) throws ChatException;

    public List<Chat> findChatsByUserId(Integer userId) throws UserException;

    public Chat createGroupChat(GroupChatRequest req, Integer reqUserId) throws UserException;

    public Chat addUserToGroupChat(Integer userId, Integer chatId) throws UserException;

    public Chat removeUserFromGroupChat(Integer chatId, Integer userId, Integer reqUserId) throws UserException, ChatException;

    public Chat renameGroup(Integer chatId, String groupName, Integer reqUserId) throws UserException, ChatException;

    public Chat deleteChat(Integer chatId, Integer userId) throws UserException, ChatException;
}
