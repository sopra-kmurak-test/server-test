package ch.uzh.ifi.hase.soprafs23.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // endpoint for receiving
        config.enableSimpleBroker("/receive");
        // endpoint for sending
        config.setApplicationDestinationPrefixes("/send");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint for starting connection
        registry.addEndpoint("/websocket").setAllowedOriginPatterns("*").withSockJS();
    }

}
