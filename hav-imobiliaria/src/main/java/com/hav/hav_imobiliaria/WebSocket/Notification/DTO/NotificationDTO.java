package com.hav.hav_imobiliaria.WebSocket.Notification.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NotificationDTO {

    private String title;
    private String content;
    private LocalDateTime dataEnvio;
    private Boolean read;
    private List<Integer> ids;
}
