package io.phyloyui.message.domain;

import io.phyloyui.message.utils.RedisConstant;

import java.io.Serializable;

public class GroupQueue implements Serializable {

    private String appKey;

    private String group;

    public String getQueueName() {
        return RedisConstant.GROUP_QUEUE + appKey + "_" + group;
    }
}
