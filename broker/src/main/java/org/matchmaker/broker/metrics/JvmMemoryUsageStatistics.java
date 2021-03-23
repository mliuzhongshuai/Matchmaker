package org.matchmaker.broker.metrics;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;

/**
 * @author Liu Zhongshuai
 * @date 2021-03-23 11:49
 **/
public class JvmMemoryUsageStatistics implements MemoryUsageStatistics {

    private final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
    private final MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

    private  final OperatingSystemMXBean systemMXBean =ManagementFactory.getOperatingSystemMXBean();


    @Override
    public double getMemoryUsage() {
        //已用内存
        long usedMemorySize = memoryUsage.getUsed();
        //最大可用内存
        long maxMemorySize = memoryUsage.getMax();

        return maxMemorySize / usedMemorySize;
    }
}
