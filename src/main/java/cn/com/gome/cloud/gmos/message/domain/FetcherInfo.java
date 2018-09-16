package cn.com.gome.cloud.gmos.message.domain;

import java.io.Serializable;
import java.util.Date;

public class FetcherInfo implements Serializable {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 键值
     */
    private String appKey;

    /**
     * 分组
     */
    private String groupName;

    /**
     * 成功数
     */
    private long successCount;

    /**
     * 失败数
     */
    private long failCount;

    /**
     * 建立连接时间
     */
    private Date connectedTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    public long getFailCount() {
        return failCount;
    }

    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }

    public Date getConnectedTime() {
        return connectedTime;
    }

    public void setConnectedTime(Date connectedTime) {
        this.connectedTime = connectedTime;
    }
}
