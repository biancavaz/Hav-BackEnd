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

    public Chat createChat(UserSecurity reqUser, Integer userId2) throws UserException, ChatException;

    public Optional<Chat> findChatById(Integer chatId) throws ChatException;

    public List<Chat> findChatsByUserId(Integer userId) throws UserException;

    public Chat createGroupChat(GroupChatRequest req, UserSecurity reqUser) throws UserException;

    public Chat addUserToGroupChat(Integer userId, Integer chatId, UserSecurity reqUser) throws UserException, ChatException;

    public Chat removeUserFromGroupChat(Integer chatId, Integer userId, UserSecurity reqUser) throws UserException, ChatException;

    public Chat renameGroup(Integer chatId, String groupName, UserSecurity reqUser) throws UserException, ChatException;

    public void deleteChat(Integer chatId, Integer userId) throws ChatException;
}
