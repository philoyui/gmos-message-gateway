package cn.com.gome.cloud.gmos.message.endpoints;

import cn.com.gome.cloud.gmos.message.domain.AckResponse;
import cn.com.gome.cloud.gmos.message.domain.ConnectRequest;
import cn.com.gome.cloud.gmos.message.service.AccessTokenService;
import cn.com.gome.cloud.gmos.message.service.WebSocketSessionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

/**
 *
 * 处理WebSocket请求核心类
 *
 */
@Component
public class WebSocketEndpoints extends TextWebSocketHandler {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Autowired
    private AccessTokenService accessTokenService;


    private Gson gson = new GsonBuilder().create();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Map<String, Object> attributes = session.getAttributes();

        String token = (String)attributes.get("token");

        ConnectRequest connectRequest = accessTokenService.resolveToken(token);

        webSocketSessionService.online(session, connectRequest);

        super.afterConnectionEstablished(session);
    }


    /**
     *
     * 收到客户端发送的消息，ACK，FAIL
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();

        AckResponse ackResponse = gson.fromJson(payload, AckResponse.class);

        webSocketSessionService.handleAckResponse(session,ackResponse);

        super.handleTextMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        webSocketSessionService.offline(session);

        super.afterConnectionClosed(session, status);
    }
}
