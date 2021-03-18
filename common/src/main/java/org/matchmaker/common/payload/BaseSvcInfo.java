package org.matchmaker.common.payload;

import org.matchmaker.common.enums.SvcRole;

/**
 * @author Liu Zhongshuai
 * @description 各服务间 payload  基本信息
 * @date 2021-03-15 15:01
 **/
public class BaseSvcInfo {

    public BaseSvcInfo(SvcRole svcRole, String ipAddress, Long timestamp) {
        this.svcRole = svcRole;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }

    /**
     * 服务角色
     */
    private SvcRole svcRole;
    /**
     * 当前服务内网ip地址
     */
    private String ipAddress;
    /**
     * 发起注册时的时间戳
     */
    private Long timestamp;


    public String getIpAddress() {
        return ipAddress;
    }

    public SvcRole getSvcRole() {
        return svcRole;
    }

    public Long getTimestamp() {
        return timestamp;
    }


}
