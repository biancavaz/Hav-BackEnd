package com.hav.hav_imobiliaria.WebSocket.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hav.hav_imobiliaria.WebSocket.Notification.Model.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientIdOrderByDataEnvioDesc(Integer userId);
}
