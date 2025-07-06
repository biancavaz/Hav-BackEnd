package com.hav.hav_imobiliaria.security.ResponseSecurity;

import com.hav.hav_imobiliaria.security.modelSecurity.UserSecurity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageWSDto {
    private Long id;
    private String content;
    private Long userId;
    private Long chatId;
    private LocalDateTime createdAt;
    private UserSecurity user;
}
