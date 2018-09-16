package io.philoyui.gateway.message.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Message implements Serializable {

    /**
     * 消息的ID
     */
    private String id;

    /**
     * 消息所属的主题
     */
    private String topic;

    /**
     * 消息发布者的AppKey
     */
    private String pubAppKey;

    /**
     * 消息的发布时间
     */
    private Date pubTime;

    /**
     * 消息所属的用户编号
     */
    private String userId;

    /**
     * 消息的详细内容
     */
    private String content;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPubAppKey() {
        return pubAppKey;
    }

    public void setPubAppKey(String pubAppKey) {
        this.pubAppKey = pubAppKey;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
