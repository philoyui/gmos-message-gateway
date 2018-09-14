package io.philoyui.gateway.message.thread;

import io.philoyui.gateway.message.domain.SubscribeRequest;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import redis.Gcache;
import redis.GcacheClient;

import java.io.IOException;
import java.util.List;

/**
 *
 * 接受Redis消息，推送给session中
 *
 * Created by yangyu-ds on 2018/9/13.
 */
public class MessageFetcher implements Runnable{

    private GcacheClient gcacheClient;

    private volatile boolean open = true;

    private SubscribeRequest subscribeRequest;

    private WebSocketSession webSocketSession;

    public MessageFetcher(WebSocketSession webSocketSession, SubscribeRequest subscribeRequest) {
        this.webSocketSession = webSocketSession;
        this.subscribeRequest = subscribeRequest;
    }

    @Override
    public void run() {
        while (open){
            List<String> messages = gcacheClient.brpop(0, subscribeRequest.buildRedisQueueName());
            for (String message : messages) {
                TextMessage textMessage = new TextMessage(message);
                try {
                    webSocketSession.sendMessage(textMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopFetcher() {
        open = false;
    }
}
