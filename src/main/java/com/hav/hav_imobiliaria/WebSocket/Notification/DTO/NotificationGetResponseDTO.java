package com.hav.hav_imobiliaria.WebSocket.Notification.DTO;

import com.hav.hav_imobiliaria.model.entity.Properties.Property;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationGetResponseDTO {

    private Integer id;
    private String title;
    private String content;
    private Boolean read;
    private LocalDateTime dataEnvio;
}
