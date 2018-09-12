package io.philoyui.gateway.message.service;

import io.philoyui.gateway.message.request.SubscribeRequest;
import org.springframework.stereotype.Component;

@Component
public class AppInfoManager {

    public boolean existAppKey(String appKey) {

        if(appKey.equalsIgnoreCase("appkey122112")){
            return true;
        }

        return false;

    }

    public String generateToken(SubscribeRequest subscribeRequest) {
        return "11111111111111";
    }
}
