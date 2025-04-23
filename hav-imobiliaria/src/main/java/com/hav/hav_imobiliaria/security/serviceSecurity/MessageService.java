package com.hav.hav_imobiliaria.security.serviceSecurity;

import com.hav.hav_imobiliaria.security.ResponseSecurity.UnreadCountDTO;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.ChatException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.MessageException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Message;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.SendMessageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    Message sendMessage(SendMessageRequest req) throws UserException, ChatException;

    List<Message> getChatsMessages(Integer chatId, UserSecurity reqUser) throws ChatException, UserException;

    Message findMessageById(Integer messageId) throws MessageException;

    void deleteMessageById(Integer messageId, UserSecurity reqUser) throws MessageException, UserException;

    List<UnreadCountDTO> getUnreadCountsForUser(UserSecurity user) throws UserException;

    // boolean messageIsRead(Integer messageId, UserSecurity user) throws UserException;
}
