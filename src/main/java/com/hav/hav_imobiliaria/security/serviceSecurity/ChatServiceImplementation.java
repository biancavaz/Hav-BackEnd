package com.hav.hav_imobiliaria.security.serviceSecurity;

import com.hav.hav_imobiliaria.model.DTO.Realtor.RealtorPutRequestDTO;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.ChatException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Chat;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import com.hav.hav_imobiliaria.security.repositorySecurity.ChatRepository;
import com.hav.hav_imobiliaria.security.requestSecurity.GroupChatRequest;
import com.hav.hav_imobiliaria.service.RealtorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatServiceImplementation implements ChatService {

    private final ChatRepository chatRepository;
    private final UserSecurityService userSecurityService;

    @Override
    public Chat createChat(UserSecurity reqUser, Integer userId2) throws UserException, ChatException {

        UserSecurity user = userSecurityService.findUserSecurityById(userId2);

        Chat isChatExist = chatRepository.findSingleChatByUsersId(user, reqUser);

        if (isChatExist != null) {
            return isChatExist;
        }

        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.set_group(false);

        return chatRepository.save(chat);
    }

    @Override
    public Optional<Chat> findChatById(Integer chatId) throws ChatException {

        Optional<Chat> chat = chatRepository.findById(chatId);

        if (chat.isPresent()) {
            return chat;
        }

        throw new ChatException("Chat not found with id " + chatId);
    }

    @Override
    public List<Chat> findChatsByUserId(Integer userId) throws UserException {

        UserSecurity user = userSecurityService.findUserSecurityById(userId);

        return chatRepository.findChatsByUserId(user.getId());
    }

    @Override
    public Chat createGroupChat(GroupChatRequest req, UserSecurity reqUser)
            throws UserException {

        Chat groupChat = new Chat();

        groupChat.set_group(true);
        groupChat.setChat_image(req.getChat_image());
        groupChat.setChat_name(req.getChat_name());
        groupChat.setCreatedBy(reqUser);
        groupChat.getAdmins().add(reqUser);

        for (Integer userId : req.getUserIds()) {
            groupChat.getUsers().add(userSecurityService.findUserSecurityById(userId));
        }

        return groupChat;
    }

    @Override
    public Chat addUserToGroupChat(Integer userId, Integer chatId, UserSecurity reqUser)
            throws UserException, ChatException {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("Chat not found with id " + chatId));

        UserSecurity user = userSecurityService.findUserSecurityById(userId);

        if (chat.getAdmins().contains(reqUser)) {
            chat.getUsers().add(user);
            return chatRepository.save(chat);
        }

        throw new UserException("You are not allowed to add this chat");
    }

    @Override
    public Chat removeUserFromGroupChat(
            Integer chatId, Integer userId, UserSecurity reqUser)
            throws UserException, ChatException {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("Chat not found with id " + chatId));

        UserSecurity user = userSecurityService.findUserSecurityById(userId);

        if (chat.getAdmins().contains(reqUser)) {
            chat.getUsers().remove(user);
            return chatRepository.save(chat);
        } else if (chat.getUsers().contains(reqUser)) {
            if (user.getId().equals(reqUser.getId())) {
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            }
        }

        throw new UserException("You are not allowed to remove this user");
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, UserSecurity reqUser)
            throws UserException, ChatException {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("Chat not found with id " + chatId));

        if (chat.getUsers().contains(reqUser)) {
            chat.setChat_name(groupName);
            return chatRepository.save(chat);
        }
        throw new UserException("You are not allowed to rename this chat");
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId)
            throws ChatException {

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new ChatException("Chat not found with id " + chatId));

        chatRepository.deleteById(chat.getId());
    }

    @Override
    public Optional<Chat> findChatByUsersId(Integer userId1, Integer userId2) throws ChatException, UserException {

        return chatRepository.findChatByTwoUsers(userId1, userId2);
    }
}
