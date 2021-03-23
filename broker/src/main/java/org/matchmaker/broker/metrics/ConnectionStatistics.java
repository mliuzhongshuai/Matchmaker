package org.matchmaker.broker.metrics;

/**
 * @author Liu Zhongshuai
 * @description broker 连接数统计

 * @date 2021-03-23 11:33
 **/
@FunctionalInterface
public interface ConnectionStatistics {


    /**
     * 获取broker 的客户端连接数
     * @return 客户端连接数
     */
    int getNumberOfConnections();
}
