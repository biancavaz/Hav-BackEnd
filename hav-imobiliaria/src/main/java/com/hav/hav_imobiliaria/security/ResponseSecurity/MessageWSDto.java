package com.hav.hav_imobiliaria.security.ResponseSecurity;

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
    private Long senderId;
    private Long chatId;
    private LocalDateTime timestamp;
}
