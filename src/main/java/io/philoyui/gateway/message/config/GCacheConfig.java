package io.philoyui.gateway.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.GcacheClient;

/**
 * Created by yangyu-ds on 2018/9/14.
 */
@Configuration
public class GCacheConfig {

    @Value("${dubbo.zookeeper.url}")
    private String dubboZookeeperUrl;

    @Bean(destroyMethod="close")
    public GcacheClient gcacheClient(){
        return new GcacheClient(dubboZookeeperUrl, "GMOS");
    }

}
