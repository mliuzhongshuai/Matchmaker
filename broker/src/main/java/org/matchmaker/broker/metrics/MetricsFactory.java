package org.matchmaker.broker.metrics;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Liu Zhongshuai
 * @description 指标工厂
 * @date 2021-03-23 13:43
 **/
public class MetricsFactory {

    private MetricsFactory() {
    }

    private static final Map<Class, Object> metricsFactory = new HashMap<>(4);

    static {
        metricsFactory.put(ConnectionStatistics.class, new BrokerClientConnectionStatistics());
        metricsFactory.put(CpuUsageStatistics.class, new HostCpuUsageStatistics());
        metricsFactory.put(MemoryUsageStatistics.class, new JvmMemoryUsageStatistics());
    }


    public static <T> T get(Class className) {
        return (T) metricsFactory.get(className);
    }
}
