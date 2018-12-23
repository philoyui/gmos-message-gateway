package io.phyloyui.message.thread;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.phyloyui.message.domain.ConnectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import redis.GcacheClient;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * 接受Redis消息，推送给session中
 *
 * Created by yangyu-ds on 2018/9/13.
 */
public class MessageFetcher implements Runnable{

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private GcacheClient gcacheClient;

    private volatile boolean open = true;

    private ConnectRequest connectRequest;

    private WebSocketSession webSocketSession;

    private AtomicLong successCount = new AtomicLong();

    private AtomicLong failCount = new AtomicLong();

    /**
     * 连接时间
     */
    private Date connectedTime = new Date();


    private Gson gson = new GsonBuilder().create();

    public MessageFetcher(WebSocketSession webSocketSession, ConnectRequest connectRequest) {
        this.webSocketSession = webSocketSession;
        this.connectRequest = connectRequest;
    }

    @Override
    public void run() {
        while (open){
            List<String> messages = gcacheClient.brpop(0, connectRequest.buildRedisQueueName());
            for (String message : messages) {
                TextMessage textMessage = new TextMessage(message);
                gson.fromJson(message,Topic);
                try {
                    webSocketSession.sendMessage(textMessage);
                    successCount.incrementAndGet();
                } catch (IOException e) {
                    LOG.error("推送消息到客户端出错",e);
                    failCount.incrementAndGet();
                }
            }
        }
    }

    public void stopFetcher() {
        open = false;
    }

    public boolean isOpen() {
        return open;
    }

    public ConnectRequest getConnectRequest() {
        return connectRequest;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public AtomicLong getSuccessCount() {
        return successCount;
    }

    public AtomicLong getFailCount() {
        return failCount;
    }

    public Date getConnectedTime() {
        return connectedTime;
    }
}
