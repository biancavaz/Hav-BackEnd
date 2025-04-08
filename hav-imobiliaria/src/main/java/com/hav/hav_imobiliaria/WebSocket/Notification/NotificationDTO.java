package com.hav.hav_imobiliaria.WebSocket.Notification;
import lombok.Data;

@Data
public class NotificationDTO {

    private Integer recipient;
    private Integer sender;
    private String content;
    private String senderName;

}
