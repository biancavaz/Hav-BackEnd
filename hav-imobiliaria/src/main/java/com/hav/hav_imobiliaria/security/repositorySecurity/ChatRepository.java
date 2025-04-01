package com.hav.hav_imobiliaria.security.repositorySecurity;

import com.hav.hav_imobiliaria.security.modelSecurity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
}
