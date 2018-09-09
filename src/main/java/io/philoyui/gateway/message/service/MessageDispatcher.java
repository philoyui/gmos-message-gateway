package io.philoyui.gateway.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Component
public class MessageDispatcher {

    @Autowired
    private SessionManager sessionManager;

    @Scheduled(cron = "0/5 * * * * ?")
    public void dispatcher(){

        WebSocketSession webSocketSession = sessionManager.findByAppKey("appkey122112");

        if(webSocketSession!=null){
            try {
                webSocketSession.sendMessage(new TextMessage("响应请求..."));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
