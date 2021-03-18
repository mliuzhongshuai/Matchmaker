package org.matchmaker.common.beat;

import java.util.List;

/**
 * @author Liu Zhongshuai
 * @description nameServer 信息
 * @date 2021-03-15 17:14
 **/
public class NameServerInfo {


    public NameServerInfo(List<String> ipAddress){
        this.ipAddress=ipAddress;
    }

    private List<String> ipAddress;

    public List<String> getIpAddress() {
        return ipAddress;
    }
}
