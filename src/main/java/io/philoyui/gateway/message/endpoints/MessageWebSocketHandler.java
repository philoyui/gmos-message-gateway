package io.philoyui.gateway.message.endpoints;

import io.philoyui.gateway.message.domain.SubscribeRequest;
import io.philoyui.gateway.message.service.AccessTokenService;
import io.philoyui.gateway.message.service.WebSocketSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Map<String, Object> attributes = session.getAttributes();

        String token = (String)attributes.get("token");

        SubscribeRequest subscribeRequest = accessTokenService.resolveToken(token);

        webSocketSessionManager.startAndFetchMessage(subscribeRequest,session);

        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        LOG.info("收到消息：" + message.getPayload());
        super.handleTextMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessionManager.offline(session);
        super.afterConnectionClosed(session, status);
    }
}
