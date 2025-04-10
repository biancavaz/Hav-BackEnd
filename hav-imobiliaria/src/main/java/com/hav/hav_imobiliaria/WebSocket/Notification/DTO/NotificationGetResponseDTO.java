package com.hav.hav_imobiliaria.WebSocket.Notification.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationGetResponseDTO {

    private String title;
    private String content;
    private Boolean read;
    private LocalDateTime dataEnvio;
}
