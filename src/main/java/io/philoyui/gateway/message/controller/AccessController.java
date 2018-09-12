package io.philoyui.gateway.message.controller;

import io.philoyui.gateway.message.domain.ResponseEntity;
import io.philoyui.gateway.message.exp.GmosException;
import io.philoyui.gateway.message.request.SubscribeRequest;
import io.philoyui.gateway.message.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yangyu-ds on 2018/9/12.
 */
@RestController
public class AccessController {

    @Autowired
    private ApplicationService applicationService;

    /**
     * 传入app_key,secret,group 注册一个客户端并生成Token，
     * @param subscribeRequest
     */
    @GetMapping("/router/ws/token")
    public ResponseEntity<String> access(SubscribeRequest subscribeRequest){
        try {
            String token = applicationService.generateToken(subscribeRequest);
            return ResponseEntity.successResponse(token);
        }catch (GmosException e){
            return ResponseEntity.errorResponse(10,e.getMessage());
        }
    }

}
