package com.hav.hav_imobiliaria.WebSocket.Service;

import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationDTO;
import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationGetResponseDTO;
import com.hav.hav_imobiliaria.WebSocket.Notification.Model.Notification;
import com.hav.hav_imobiliaria.WebSocket.Repository.NotificationRepository;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationService {

    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void salvarNotificacao(NotificationDTO notificationdto) {
        Notification not = new Notification();
        not.setTitle(notificationdto.getTitle());
        not.setContent(notificationdto.getContent());
        not.setRead(false);
        not.setDataEnvio(LocalDateTime.now());
        notificationRepository.save(not);

        List<User> recipients = userRepository.findAllById(notificationdto.getIds());
        for (User user : recipients) {
            user.getNotifications().add(not);
            userRepository.save(user);
        }
        not.setRecipient(recipients);
        enviarNotificacao(notificationdto);
    }

    public void enviarNotificacao(NotificationDTO notification) {
        NotificationGetResponseDTO dto = new NotificationGetResponseDTO();
        dto.setTitle(notification.getTitle());
        dto.setContent(notification.getContent());
        dto.setRead(notification.getRead());
        dto.setDataEnvio(notification.getDataEnvio());
        for (Integer id : notification.getIds()) {
            System.out.println("Enviando para /topic/notifications/" + id);
            simpMessagingTemplate.convertAndSend("topic/notifications" + id, dto);
        }
    }

    public void deletarNotificacao(Integer id) {
        Notification not = notificationRepository.findById(Long.valueOf(id)).orElseThrow(() ->
                new RuntimeException("Notificação não encontrada"));
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.getNotifications().remove(not);
            userRepository.save(user);
        }
        notificationRepository.delete(not);
    }

    public List<NotificationGetResponseDTO> notifications(Integer userId) {
        List<Notification> notifications = notificationRepository.findByRecipientIdOrderByDataEnvioDesc(userId);
        return notifications.stream().map(n ->
                        new NotificationGetResponseDTO(
                                n.getTitle(),
                                n.getContent(),
                                n.getRead(),
                                n.getDataEnvio()))
                .collect(Collectors.toList());
    }

    public void marcarComoLida(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() ->
                new RuntimeException("Notificação não encontrada"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
