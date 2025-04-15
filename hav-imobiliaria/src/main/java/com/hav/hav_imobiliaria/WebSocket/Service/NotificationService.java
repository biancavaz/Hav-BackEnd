package com.hav.hav_imobiliaria.WebSocket.Service;

import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationDTO;
import com.hav.hav_imobiliaria.WebSocket.Notification.DTO.NotificationGetResponseDTO;
import com.hav.hav_imobiliaria.WebSocket.Notification.Model.Notification;
import com.hav.hav_imobiliaria.WebSocket.Repository.NotificationRepository;
import com.hav.hav_imobiliaria.model.entity.Users.User;
import com.hav.hav_imobiliaria.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
        System.out.println("ID gerado: " + not.getId());

        List<User> recipients = userRepository.findAllById(notificationdto.getIds());
        for (User user : recipients) {
            user.getNotifications().add(not);
            userRepository.save(user);
        }
        not.setRecipient(recipients);

        // Convertendo a notificação salva para DTO
        NotificationGetResponseDTO dto = new NotificationGetResponseDTO(
                not.getId(),
                not.getTitle(),
                not.getContent(),
                not.getRead(),
                not.getDataEnvio()
        );
        notificationPreferredPropertyType(dto, notificationdto.getIds());
        enviarNotificacao(dto, notificationdto.getIds());
    }

    public void enviarNotificacao(NotificationGetResponseDTO dto, List<Integer> ids) {
        for (Integer id : ids) {
            String destino = "/topic/api/" + id;
            System.out.println("Enviando para: " + destino + " | ID da notificação: " + dto.getId());
            simpMessagingTemplate.convertAndSend(destino, dto);
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
                                n.getId(),
                                n.getTitle(),
                                n.getContent(),
                                n.getRead(),
                                n.getDataEnvio()))
                .collect(Collectors.toList());
    }

    public void marcarComoLida(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId.longValue()).orElseThrow(() ->
                new RuntimeException("Notificação não encontrada"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    public void notificationPreferredPropertyType(NotificationGetResponseDTO dto, List<Integer> ids){
        for (Integer id : ids) {
            String destino = "/topic/api/" + id;
            System.out.println("Enviando para: " + destino + " | ID da notificação: " + dto.getId());
            simpMessagingTemplate.convertAndSend(destino, dto);
        }
    }

    public void salvarNotificacaoDePreferencia(User user, NotificationGetResponseDTO not) {
        Notification notification = new Notification();
        notification.setTitle(not.getTitle());
        notification.setContent(not.getContent());
        notification.setRead(false);
        notification.setDataEnvio(LocalDateTime.now());

        notificationRepository.save(notification);

        user.getNotifications().add(notification);
        userRepository.save(user);

        notification.setRecipient(List.of(user));

        NotificationGetResponseDTO dto = new NotificationGetResponseDTO(
                notification.getId(),
                notification.getTitle(),
                notification.getContent(),
                notification.getRead(),
                notification.getDataEnvio()
        );

        enviarNotificacao(dto, List.of(user.getId()));
    }

}
