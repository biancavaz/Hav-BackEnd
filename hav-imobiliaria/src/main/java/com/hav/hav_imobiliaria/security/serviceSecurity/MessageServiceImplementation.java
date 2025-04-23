package com.hav.hav_imobiliaria.security.serviceSecurity;

import com.hav.hav_imobiliaria.repository.UserRepository;
import com.hav.hav_imobiliaria.security.ResponseSecurity.UnreadCountDTO;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.ChatException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.MessageException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Chat;
import com.hav.hav_imobiliaria.security.modelSecurity.Message;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.MessageRepository;
import com.hav.hav_imobiliaria.security.repositorySecurity.UserRepositorySecurity;
import com.hav.hav_imobiliaria.security.requestSecurity.SendMessageRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImplementation implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepositorySecurity userRepositorySecurity;
    private final ChatService chatService;

    @Override
    public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {

        Optional<UserSecurity> user = userRepositorySecurity.findById(req.getUserId());

        Optional<Chat> chat = chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat.get());
        message.setUser(user.get());
        message.setContent(req.getContent());
        message.setCreatedAt(LocalDateTime.now());
        message.setIsRead(false);

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatsMessages(Integer chatId, UserSecurity reqUser) throws ChatException, UserException {

        Optional<Chat> chat = chatService.findChatById(chatId);

        if (!chat.get().getUsers().contains(reqUser)) {
            throw new UserException("You are not authorized to view this message" + chat.get().getId());
        }

        return messageRepository.findByChatId(chat.get().getId());
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {

        return messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageException("Message not found with id " + messageId));
    }

    @Override
    public void deleteMessageById(Integer messageId, UserSecurity reqUser) throws MessageException, UserException {

        Message message = findMessageById(messageId);

        if (message.getUser().getId().equals(reqUser.getId())) {
            messageRepository.delete(message);
        }

        throw new UserException("You are not authorized to delete this message" + message.getId());
    }

    @Override
    public List<UnreadCountDTO> getUnreadCountsForUser(UserSecurity user) throws UserException {
        List<Chat> userChats = chatService.findChatsByUserId(user.getId());

        List<UnreadCountDTO> result = new ArrayList<>();

        for (Chat chat : userChats) {
            int count = messageRepository.countUnreadMessages(chat.getId(), user.getId());
            result.add(new UnreadCountDTO(chat.getId(), count));
        }

        return result;
    }

    @Override
    public void markMessagesAsRead(Integer chatId, Integer userId) {
        messageRepository.markMessagesAsRead(chatId, userId);
    }

}
