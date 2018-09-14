package io.philoyui.gateway.message.dispatcher;

import io.philoyui.gateway.message.domain.TopicMessage;
import io.philoyui.gateway.message.domain.TopicQueueGroup;
import io.philoyui.gateway.message.service.TopicGroupManager;
import redis.Gcache;

import java.util.List;

/**
 * Created by yangyu-ds on 2018/9/13.
 */
public class MessageDispatcher {

    private TopicGroupManager topicGroupManager;

    private Gcache gcache;

    /**
     * 获取订阅这个Topic的队列组，Topic -> AppKey + GroupName
     * push到redis中的队列
     */
    public void dispatcher(){
        List<TopicMessage> topicMessages = fetchMessages();
        for (TopicMessage topicMessage : topicMessages) {
            String topic = topicMessage.getTopic();
            List<TopicQueueGroup> topicQueueGroups = topicGroupManager.findByTopic(topic);
            for (TopicQueueGroup topicQueueGroup : topicQueueGroups) {
                gcache.rpush(topicQueueGroup.getQueueName(),topicMessage.getPayload());
            }
        }
    }

    private List<TopicMessage> fetchMessages() {
        return null;
    }

}