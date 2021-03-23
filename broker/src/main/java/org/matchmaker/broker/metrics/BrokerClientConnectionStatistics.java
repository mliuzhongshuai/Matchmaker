package org.matchmaker.broker.metrics;

/**
 * @author Liu Zhongshuai
 * @description broker 连接数统计
 * @date 2021-03-23 11:33
 **/
public class BrokerClientConnectionStatistics implements ConnectionStatistics {


    @Override
    public int getNumberOfConnections() {
        return 0;
    }
}
