package io.philoyui.gateway.message.dao;

import io.philoyui.gateway.message.domain.SubscribeApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeApplicationDao extends JpaRepository<SubscribeApplication,String> {

    SubscribeApplication findByAppKey(String appKey);

}
