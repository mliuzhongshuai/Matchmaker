package org.matchmaker.common.enums;

/**
 * @author Liu Zhongshuai
 * @description broker 所支持的协议
 * @date 2021-03-16 11:51
 **/
public enum BrokerServerSupportProtocol {

    MQTT,
    MQTT_WS,
    MQTT_WS_PAHO;

    BrokerServerSupportProtocol(){}

}
