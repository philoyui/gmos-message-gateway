package cn.com.gome.cloud.gmos.message.config;

import cn.com.gome.cloud.gmos.message.endpoints.WebSocketEndpoints;
import cn.com.gome.cloud.gmos.message.interceptor.MessageHandshakeInterceptor;
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
    private WebSocketEndpoints webSocketEndpoints;

    @Autowired
    private MessageHandshakeInterceptor messageHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketEndpoints,"/endpoints").addInterceptors(messageHandshakeInterceptor);
    }

}
