package org.matchmaker.broker.metrics;

/**
 * @author Liu Zhongshuai
 * @description 内存使用情况
 * @date 2021-03-23 11:35
 **/
@FunctionalInterface
public interface MemoryUsageStatistics {

    /**
     * 内存使用率(已用内存/最大可用内存)
     *
     * @return 内存使用率
     */
    double getMemoryUsage();
}
