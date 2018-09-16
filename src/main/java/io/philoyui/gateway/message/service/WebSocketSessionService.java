package io.philoyui.gateway.message.service;

import io.philoyui.gateway.message.domain.AckResponse;
import io.philoyui.gateway.message.domain.ConnectRequest;
import io.philoyui.gateway.message.interfaze.ApplicationCommandService;
import io.philoyui.gateway.message.thread.MessageFetcher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionService {

    private Map<String,MessageFetcher> messageFetchers = new ConcurrentHashMap<>();

    /**
     *
     * 应用上线，启动监听 appKey + group 队列
     *
     * 应用开启抓取消息线程
     *
     * @param session
     * @param connectRequest
     */
    public void online(WebSocketSession session, ConnectRequest connectRequest) {
        MessageFetcher messageFetcher = new MessageFetcher(session, connectRequest);
        new Thread(messageFetcher).start();
        messageFetchers.put(session.getId(),messageFetcher);

    }

    /**
     * 应用下线
     * @param session
     */
    public void offline(WebSocketSession session) {
        MessageFetcher messageFetcher = messageFetchers.remove(session.getId());
        messageFetcher.stopFetcher();
    }

    public Map<String, MessageFetcher> getMessageFetchers() {
        return messageFetchers;
    }

    /**
     * 处理Ack请求
     * @param session
     * @param ackResponse
     */
    public void handleAckResponse(WebSocketSession session, AckResponse ackResponse) {

    }
}
