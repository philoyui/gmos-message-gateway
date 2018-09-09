package io.philoyui.gateway.message.config;

import io.philoyui.gateway.message.endpoints.MessageWebSocketHandler;
import io.philoyui.gateway.message.interceptor.MessageHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@EnableWebMvc
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private MessageWebSocketHandler messageWebSocketHandler;

    @Autowired
    private MessageHandshakeInterceptor messageHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(messageWebSocketHandler,"/endpoints").addInterceptors(messageHandshakeInterceptor);
    }

}
