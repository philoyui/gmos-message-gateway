package io.philoyui.gateway.message.domain;

import java.io.Serializable;

/**
 *
 * 订阅的应用
 *
 * Created by yangyu-ds on 2018/9/12.
 */
public class SubscribeApplication implements Serializable{

    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
