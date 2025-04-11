package com.hav.hav_imobiliaria.WebSocket.Notification.DTO;

import lombok.Data;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private Integer id;
    private String title;
    private String content;
    private LocalDateTime dataEnvio;
    private Boolean read;
    private List<Integer> ids;
}
