package org.matchmaker.broker.metrics;

/**
 * @author Liu Zhongshuai
 * @description 内存使用情况
 * @date 2021-03-23 11:35
 **/
@FunctionalInterface
public interface CpuUsageStatistics {

    /**
     * 宿主机cpu使用率
     *
     * @return cpu使用率
     */
    double getCpuUsage();
}
