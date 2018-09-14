package io.philoyui.gateway.message.controller;

import io.philoyui.gateway.message.domain.ResponseEntity;
import io.philoyui.gateway.message.exp.GmosException;
import io.philoyui.gateway.message.domain.SubscribeRequest;
import io.philoyui.gateway.message.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 用于接入远程应用的注册请求
 *
 * Created by yangyu-ds on 2018/9/12.
 */
@RestController
public class AccessController {

    @Autowired
    private AccessTokenService accessTokenService;

    /**
     *
     * 传入app_key,secret,group 注册一个客户端并生成Token，
     *
     * @param subscribeRequest
     */
    @GetMapping("/router/ws/token")
    public ResponseEntity<String> access(SubscribeRequest subscribeRequest){
        try {
            String token = accessTokenService.generateToken(subscribeRequest);
            return ResponseEntity.successResponse(token);
        }catch (GmosException e){
            return ResponseEntity.errorResponse(10,e.getMessage());
        }
    }

}
