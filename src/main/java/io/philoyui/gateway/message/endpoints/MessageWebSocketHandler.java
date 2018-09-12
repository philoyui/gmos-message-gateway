package io.philoyui.gateway.message.endpoints;

import io.philoyui.gateway.message.service.ApplicationService;
import io.philoyui.gateway.message.service.SessionManager;
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
    private SessionManager sessionManager;

    @Autowired
    private ApplicationService applicationService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Map<String, Object> attributes = session.getAttributes();
        String appKey = (String)attributes.get("appKey");
        sessionManager.online(appKey,session);
        LOG.info("应用已上线：" + appKey);
        super.afterConnectionEstablished(session);

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        LOG.info("收到消息：" + message.getPayload());

        super.handleTextMessage(session, message);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

//        exception.printStackTrace();

        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        Map<String, Object> attributes = session.getAttributes();

        String appKey = (String)attributes.get("appKey");

        sessionManager.offline(appKey,session);

        LOG.info("应用已下线：" + appKey);

        super.afterConnectionClosed(session, status);

    }
}
