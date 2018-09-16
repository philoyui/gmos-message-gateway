package cn.com.gome.cloud.gmos.message.dispatcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cn.com.gome.cloud.gmos.message.domain.Application;
import cn.com.gome.cloud.gmos.message.domain.GroupQueue;
import cn.com.gome.cloud.gmos.message.domain.Message;
import cn.com.gome.cloud.gmos.message.domain.TopicMessage;
import cn.com.gome.cloud.gmos.message.service.ApplicationService;
import cn.com.gome.cloud.gmos.message.service.GroupQueueService;
import org.springframework.stereotype.Component;
import redis.Gcache;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yangyu-ds on 2018/9/13.
 */
@Component
public class MessageDispatcher {

    private Gcache gcache;

    private ApplicationService applicationService;

    private GroupQueueService groupQueueService;

    private Gson gson = new GsonBuilder().create();

    /**
     * 获取订阅这个Topic的队列组，Topic -> AppKey + GroupName
     * push到redis中的队列
     */
    public void dispatcher(){

        List<TopicMessage> topicMessages = fetchMessages();

        for (TopicMessage topicMessage : topicMessages) {
            
            String topic = topicMessage.getTopic();

            List<Application> applications = applicationService.findByTopic(topic);

            for (Application application : applications) {

                List<GroupQueue> groupQueues = groupQueueService.findGroupsByAppKey(application.getAppKey());

                for (GroupQueue groupQueue : groupQueues) {

                    Message message = new Message();
                    message.setId(UUID.randomUUID().toString());
                    message.setContent(topicMessage.getPayload());
                    message.setPubAppKey(application.getAppKey());
                    message.setPubTime(new Date());
                    message.setTopic(topic);
                    message.setUserId(topicMessage.getVenderId());
                    String topicMessageJson = gson.toJson(topicMessage);
                    gcache.rpush(groupQueue.getQueueName(),topicMessageJson);
                }

            }

        }
    }

    private List<TopicMessage> fetchMessages() {
        return null;
    }

}