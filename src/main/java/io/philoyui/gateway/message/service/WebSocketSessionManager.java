package io.philoyui.gateway.message.service;

import io.philoyui.gateway.message.domain.SubscribeRequest;
import io.philoyui.gateway.message.thread.MessageFetcher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {

    private Map<String,MessageFetcher> messageFetchers = new ConcurrentHashMap<>();


    /**
     *
     * 应用上线，启动监听 appKey + group 队列
     *
     * 应用开启抓取消息线程
     *
     * @param subscribeRequest
     * @param session
     */
    public void startAndFetchMessage(SubscribeRequest subscribeRequest, WebSocketSession session) {
        MessageFetcher messageFetcher = new MessageFetcher(session, subscribeRequest);
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
}
