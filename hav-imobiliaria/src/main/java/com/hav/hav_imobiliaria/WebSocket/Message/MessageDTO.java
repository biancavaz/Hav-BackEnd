package com.hav.hav_imobiliaria.WebSocket.Message;
import lombok.Data;

@Data
public class MessageDTO {

    private Integer recipient;
    private Integer sender;
    private String content;
    private String senderName;

}
