package org.matchmaker.common.payload;

import org.matchmaker.common.enums.BrokerServerSupportProtocol;
import org.matchmaker.common.enums.SvcRole;

/**
 * @author Liu Zhongshuai
 * @description broker 服务信息上报
 * @date 2021-03-15 15:05
 **/
public class BrokerSvcInfo extends BaseSvcInfo {

    public BrokerSvcInfo(String ipAddress, Integer netSocketNum) {

        super(SvcRole.BROKER, ipAddress, System.currentTimeMillis());

        this.netSocketNum = netSocketNum;
    }

    /**
     * 外网地址 xxxxx:prot
     */
    private String internetAddress;

    /**
     * 当前服务网络连接数
     */
    private Integer netSocketNum;

    /**
     * 当前broker 所支持的协议
     */
    private BrokerServerSupportProtocol supportProtocol;

    public BrokerServerSupportProtocol getSupportProtocol() {
        return supportProtocol;
    }

    public Integer getNetSocketNum() {
        return netSocketNum;
    }

}
