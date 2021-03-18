package org.matchmaker.nameserver.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Liu Zhongshuai
 * @description 判别svc是否健康的相关策略
 * @date 2021-03-17 14:17
 **/
@ConfigurationProperties(prefix = "svc-health-check")
public class SvcHealthScheduledStrategyProperties {

    /**
     * 周期性检查间隔 默认 2s检查一次
     */
    private int checkInterval=2;





}
