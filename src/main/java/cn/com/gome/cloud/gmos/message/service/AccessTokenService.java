package cn.com.gome.cloud.gmos.message.service;

import cn.com.gome.cloud.gmos.message.exp.GmosException;
import cn.com.gome.cloud.gmos.message.utils.SignUtils;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cn.com.gome.cloud.gmos.message.domain.Application;
import cn.com.gome.cloud.gmos.message.domain.ConnectRequest;
import cn.com.gome.cloud.gmos.message.utils.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.GcacheClient;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 *
 * 用于维护各个请求的访问令牌
 *
 * Created by yangyu-ds on 2018/9/14.
 */
@Component
public class AccessTokenService {

    @Autowired
    private GcacheClient gcacheClient;

    @Autowired
    private ApplicationService applicationService;

    private Gson gson = new GsonBuilder().create();

    /**
     *
     * 为请求应用生成访问令牌，放到缓存中
     *
     * @param connectRequest
     * @return
     */
    public String generateToken(ConnectRequest connectRequest) {

        Application application = checkAndGetApp(connectRequest.getAppKey());

        checkSign(connectRequest, application.getSecret());

        String token = UUID.randomUUID().toString();

        gcacheClient.setex(buildTokenRedisKey(token),5 * 60,gson.toJson(connectRequest));

        return token;
    }

    /**
     *
     * @param token
     * @return
     */
    private String buildTokenRedisKey(String token) {
        return RedisConstant.TOKEN_KEY + "_" + token;
    }

    /**
     *
     * 对接收到的请求进行签名
     *
     * @param connectRequest
     * @param secret
     * @return
     */
    private void checkSign(ConnectRequest connectRequest, String secret) {

        Map<String,String> parameters = new TreeMap<>();
        parameters.put("appKey", connectRequest.getAppKey());
        parameters.put("groupName", connectRequest.getGroupName());
        parameters.put("version", connectRequest.getVersion());
        parameters.put("timestamp",String.valueOf(connectRequest.getTimestamp()));
        String serverSign = SignUtils.sign(parameters,secret);
        if(connectRequest.getSign().equalsIgnoreCase(serverSign)){
            throw new GmosException("签名错误，可能是appKey或secret信息不正确");
        }

    }

    /**
     * 校验appKey是否存在
     * @param appKey
     * @return
     */
    private Application checkAndGetApp(String appKey) {

        Application subscribeApplication = applicationService.findByAppKey(appKey);

        if(subscribeApplication==null){
            throw new GmosException("appKey对应的应用不存在");
        }

        return subscribeApplication;

    }

    /**
     * 解析Token为Request
     * @param token
     * @return
     */
    public ConnectRequest resolveToken(String token) {

        String redisTokenResult = gcacheClient.get(buildTokenRedisKey(token));

        if(!Strings.isNullOrEmpty(redisTokenResult)){
            return gson.fromJson(redisTokenResult, ConnectRequest.class);
        }

        throw new GmosException("请求token找不到" + token);
    }
}
