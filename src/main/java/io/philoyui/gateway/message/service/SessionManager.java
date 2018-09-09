package io.philoyui.gateway.message.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private Map<String,WebSocketSession> socketSessionMap = new ConcurrentHashMap<>();

    /**
     *
     * 上线
     *
     * @param appKey
     * @param session
     */
    public void online(String appKey, WebSocketSession session) {
        socketSessionMap.put(appKey,session);
    }

    /**
     *
     * 下线
     *
     * @param appKey
     * @param session
     */
    public void offline(String appKey, WebSocketSession session) {
        socketSessionMap.remove(appKey,session);
    }

    /**
     * 根据AppKey查找对应的WebSocketSession
     * @param appKey
     * @return
     */
    public WebSocketSession findByAppKey(String appKey) {
        return socketSessionMap.get(appKey);
    }

}
