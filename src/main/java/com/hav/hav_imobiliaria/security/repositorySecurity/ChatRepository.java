package com.hav.hav_imobiliaria.security.repositorySecurity;

import com.hav.hav_imobiliaria.security.exceptionsSecurity.ChatException;
import com.hav.hav_imobiliaria.security.exceptionsSecurity.UserException;
import com.hav.hav_imobiliaria.security.modelSecurity.Chat;
import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("select c from Chat c join c.users u where u.id=:userId")
    List<Chat> findChatsByUserId(@Param("userId") Integer userId) throws UserException;

    @Query("select  c from Chat c where c.is_group=false and " +
            ":user member of c.users and " +
            ":reqUser member of c.users")
    Chat findSingleChatByUsersId(
            @Param("user") UserSecurity userSecurity,
            @Param("reqUser") UserSecurity reqUser) throws ChatException;

    @Query("""
                SELECT c FROM Chat c
                JOIN c.users u
                WHERE u.id IN (:userId1, :userId2)
                GROUP BY c.id
                HAVING COUNT(DISTINCT u.id) = 2
            """)
    Optional<Chat> findChatByTwoUsers(@Param("userId1") Integer userId1,
                                      @Param("userId2") Integer userId2);

}
