package org.matchmaker.broker.metrics;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * @author Liu Zhongshuai
 * @description 内存使用情况
 * @date 2021-03-23 11:35
 **/
public class HostCpuUsageStatistics implements CpuUsageStatistics{

    private  final OperatingSystemMXBean systemMXBean =  ManagementFactory.getOperatingSystemMXBean();

    @Override
    public double getCpuUsage() {
        return systemMXBean.getSystemLoadAverage();
    }
}
