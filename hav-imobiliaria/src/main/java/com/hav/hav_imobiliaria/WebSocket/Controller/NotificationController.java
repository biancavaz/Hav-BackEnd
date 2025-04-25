package com.hav.hav_imobiliaria.WebSocket.Controller;

import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationDTO;
import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationGetResponseDTO;
import com.hav.hav_imobiliaria.WebSocket.Notification.Model.Notification;
import com.hav.hav_imobiliaria.WebSocket.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-notification")
    public ResponseEntity<Void> enviarMensagem(@RequestBody NotificationDTO notification) {
        notificationService.salvarNotificacao(notification);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public void deletarMensagem(@PathVariable Integer id) {
        notificationService.deletarNotificacao(id);
    }

    @GetMapping("/getNotifications")
    public List<NotificationGetResponseDTO> getNotifications(@CookieValue("token")
                                                             String token) {
        System.out.println("TOKEN RECEBIDO: " + token);
        return notificationService.notifications(token);
    }

    @PutMapping("/readNotification/{id}")
    public ResponseEntity<Void> marcarComoLida(@PathVariable("id") Integer id) {
        notificationService.marcarComoLida(id);
        return ResponseEntity.ok().build();
    }


}
