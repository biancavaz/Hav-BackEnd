package com.hav.hav_imobiliaria.security.configSecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000").withSockJS();
//        // registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.setApplicationDestinationPrefixes("/app");
//        config.enableSimpleBroker("/group", "/user");
//        config.setUserDestinationPrefix("/user");
//    }
//}

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns("*").withSockJS(); // endpoint de conexão
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // canal de broadcast
        registry.setApplicationDestinationPrefixes("/app"); // prefixo usado pelo cliente para enviar msg
    }
}
