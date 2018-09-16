package io.philoyui.gateway.message.domain;

/**
 * Created by yangyu-ds on 2018/9/14.
 */
public class Application {

    private String appKey;

    private String secret;

    public String getSecret() {
        return secret;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
