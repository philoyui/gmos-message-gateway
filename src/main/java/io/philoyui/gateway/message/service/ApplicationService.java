package io.philoyui.gateway.message.service;

import io.philoyui.gateway.message.dao.SubscribeApplicationDao;
import io.philoyui.gateway.message.domain.SubscribeApplication;
import io.philoyui.gateway.message.exp.GmosException;
import io.philoyui.gateway.message.request.SubscribeRequest;
import io.philoyui.gateway.message.utils.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApplicationService {

    private Map<String,SubscribeApplication> appInfoService = new ConcurrentHashMap<>();

    @Autowired
    private SubscribeApplicationDao subscribeApplicationDao;


    public boolean existAppKey(String appKey) {

        if(appKey.equalsIgnoreCase("appkey122112")){
            return true;
        }

        return false;

    }

    /**
     *
     * 校验签名，签名错误则提示appKey或secret信息不正确
     *
     * @param subscribeRequest
     * @return
     */
    public String generateToken(SubscribeRequest subscribeRequest) {

        SubscribeApplication subscribeApplication = checkAndGetApplication(subscribeRequest.getAppKey());

        String serverSign = signRequest(subscribeRequest, subscribeApplication.getSecret());

        if(subscribeRequest.getSign().equalsIgnoreCase(serverSign)){
            throw new GmosException("签名错误，可能是appKey或secret信息不正确");
        }

        return UUID.randomUUID().toString();
    }

    /**
     *
     * 对接收到的请求进行签名
     *
     * @param subscribeRequest
     * @param secret
     * @return
     */
    private String signRequest(SubscribeRequest subscribeRequest, String secret) {
        Map<String,String> parameters = new TreeMap<>();
        parameters.put("appKey",subscribeRequest.getAppKey());
        parameters.put("groupName",subscribeRequest.getGroupName());
        parameters.put("version",subscribeRequest.getVersion());
        parameters.put("timestamp",String.valueOf(subscribeRequest.getTimestamp()));
        return SignUtils.sign(parameters,secret);
    }

    /**
     * 校验appKey是否存在
     * @param appKey
     * @return
     */
    private SubscribeApplication checkAndGetApplication(String appKey) {

        SubscribeApplication subscribeApplication = subscribeApplicationDao.findByAppKey(appKey);

        if(subscribeApplication==null){
            throw new GmosException("appKey对应的应用不存在");
        }

        return subscribeApplication;
    }

}
