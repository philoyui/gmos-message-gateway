package io.philoyui.gateway.message.service;

import io.philoyui.gateway.message.domain.SubscribeApplication;
import io.philoyui.gateway.message.exp.GmosException;
import io.philoyui.gateway.message.request.SubscribeRequest;
import io.philoyui.gateway.message.utils.SignUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApplicationService {

    private Map<String,SubscribeApplication> appInfoService = new ConcurrentHashMap<>();

    public boolean existAppKey(String appKey) {

        if(appKey.equalsIgnoreCase("appkey122112")){
            return true;
        }

        return false;

    }

    /**
     *
     * 校验appKey是否存在
     *
     * 校验签名，签名错误则提示appKey或secret信息不正确
     *
     * @param subscribeRequest
     * @return
     */
    public String generateToken(SubscribeRequest subscribeRequest) {

        String appKey = subscribeRequest.getAppKey();

        SubscribeApplication subscribeApplication = checkAndGetApplication(appKey);

        Map<String,String> parameters = new TreeMap<>();
        parameters.put("appKey",appKey);
        parameters.put("groupName",subscribeRequest.getGroupName());
        parameters.put("version",subscribeRequest.getVersion());
        parameters.put("timestamp",String.valueOf(subscribeRequest.getTimestamp()));
        String serverSign = SignUtils.sign(parameters,subscribeApplication.getSecret());

        if(subscribeRequest.getSign().equalsIgnoreCase(serverSign)){
            throw new GmosException("签名错误，可能是appKey或secret信息不正确");
        }



        return UUID.randomUUID().toString();
    }

    private SubscribeApplication checkAndGetApplication(String appKey) {



//        throw new GmosException("请求的应用不存在");


        return new SubscribeApplication();
    }
}
