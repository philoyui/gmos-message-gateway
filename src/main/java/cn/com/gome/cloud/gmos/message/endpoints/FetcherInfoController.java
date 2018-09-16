package cn.com.gome.cloud.gmos.message.endpoints;

import cn.com.gome.cloud.gmos.message.domain.FetcherInfo;
import cn.com.gome.cloud.gmos.message.service.WebSocketSessionService;
import cn.com.gome.cloud.gmos.message.thread.MessageFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fetcher")
public class FetcherInfoController {

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    public List<FetcherInfo> fetchInfo(){

        List<FetcherInfo> fetcherInfos = new ArrayList<>();

        Map<String, MessageFetcher> messageFetchers = webSocketSessionService.getMessageFetchers();

        for (Map.Entry<String, MessageFetcher> stringMessageFetcherEntry : messageFetchers.entrySet()) {

            MessageFetcher messageFetcher = stringMessageFetcherEntry.getValue();

            FetcherInfo fetcherInfo = new FetcherInfo();
            fetcherInfo.setSessionId(stringMessageFetcherEntry.getKey());
            fetcherInfo.setAppKey(messageFetcher.getConnectRequest().getAppKey());
            fetcherInfo.setFailCount(messageFetcher.getFailCount().get());
            fetcherInfo.setGroupName(messageFetcher.getConnectRequest().getGroupName());
            fetcherInfo.setSuccessCount(messageFetcher.getSuccessCount().get());
            fetcherInfo.setConnectedTime(messageFetcher.getConnectedTime());

            fetcherInfos.add(fetcherInfo);

        }

        return fetcherInfos;
    }

}
