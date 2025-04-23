package com.hav.hav_imobiliaria.security.repositorySecurity;

import com.hav.hav_imobiliaria.security.modelSecurity.Chat;
import com.hav.hav_imobiliaria.security.modelSecurity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("select m from Message m join m.chat c where c.id=:chatId")
    List<Message> findByChatId(@Param("chatId") Integer chatId);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.chat.id = :chatId AND m.user.id <> :userId AND m.isRead = false")
    int countUnreadMessages(@Param("chatId") Integer chatId, @Param("userId") Integer userId);

}
