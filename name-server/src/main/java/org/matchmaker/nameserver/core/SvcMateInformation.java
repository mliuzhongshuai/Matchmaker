package org.matchmaker.nameserver.core;

import org.matchmaker.common.payload.BaseSvcInfo;

import java.util.Objects;

/**
 * @author Liu Zhongshuai
 * @description 服务健康信息
 * @date 2021-03-16 16:40
 **/
public class SvcMateInformation {


    public SvcMateInformation(BaseSvcInfo svcInfo) {
        this.svcInfo = svcInfo;
    }

    /**
     * svc 信息
     */
    private BaseSvcInfo svcInfo;

    /**
     * 服务是否正常
     */
    private boolean isUp = Boolean.TRUE;

    /**
     * 注册、刷新时间戳
     */
    private Long refreshTimestamp=System.currentTimeMillis();


    public Long getRefreshTimestamp() {
        return refreshTimestamp;
    }


    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public BaseSvcInfo getSvcInfo() {
        return svcInfo;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof SvcMateInformation) {
            return this.svcInfo.getIpAddress().equals(((SvcMateInformation) o).svcInfo.getIpAddress());
        }
        return Boolean.FALSE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(svcInfo.getIpAddress());
    }
}
