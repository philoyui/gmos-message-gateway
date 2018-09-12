package io.philoyui.gateway.message.interceptor;

import io.philoyui.gateway.message.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class MessageHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationService applicationService;

    /**
     *
     * 在创建WebSocket连接时，获取来自哪个应用的连接请求，验证请求参数是否正确
     * 验证成功：通过请求，设置当前的应用信息到Session中
     * 验证不成功：提示错误？？？？？？？？？？
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        String appKey = servletRequest.getParameter("appKey");

        if(applicationService.existAppKey(appKey)){
            attributes.put("appKey",appKey);
            return super.beforeHandshake(request, response, wsHandler, attributes);
        } else {
            LOG.error("不存在的应用：" + appKey);
            return false;
        }

    }



}
