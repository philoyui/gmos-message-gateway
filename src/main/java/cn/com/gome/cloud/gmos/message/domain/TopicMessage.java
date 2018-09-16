package cn.com.gome.cloud.gmos.message.domain;

import java.io.Serializable;

/**
 * Created by yangyu-ds on 2018/9/13.
 */
public class TopicMessage implements Serializable {

    private String id;

    private String venderId;

    private String topic;

    private String payload;

    public String getTopic() {
        return topic;
    }

    public String getPayload() {
        return payload;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }
}
