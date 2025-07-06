package com.hav.hav_imobiliaria.security.requestSecurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {

    private Integer userId;
    private Integer chatId;
    private String content;
}
