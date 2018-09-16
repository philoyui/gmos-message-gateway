package cn.com.gome.cloud.gmos.message.service;

import cn.com.gome.cloud.gmos.message.domain.GroupQueue;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息分组队列管理
 */
@Component
public class GroupQueueService {

    public List<GroupQueue> findGroupsByAppKey(String appKey) {
        return null;
    }

}
