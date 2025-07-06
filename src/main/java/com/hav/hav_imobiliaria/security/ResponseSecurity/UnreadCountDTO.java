package com.hav.hav_imobiliaria.security.ResponseSecurity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnreadCountDTO {

    private Integer chatId;
    private int unreadCount;
}
