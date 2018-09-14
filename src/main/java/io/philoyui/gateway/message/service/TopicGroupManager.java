package io.philoyui.gateway.message.service;

import io.philoyui.gateway.message.domain.TopicQueueGroup;

import java.util.List;

/**
 *
 * 队列Group管理 TopicName -> List<Group>
 *
 * 应用上线：加入Queue
 * 应用下线：移除Queue
 *
 * 应用订阅主题：
 * 应用取消订阅主题：
 *
 * Created by yangyu-ds on 2018/9/13.
 */
public class TopicGroupManager {

    public List<TopicQueueGroup> findByTopic(String topic) {
        return null;
    }

}
