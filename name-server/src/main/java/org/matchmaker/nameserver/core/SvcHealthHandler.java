package org.matchmaker.nameserver.core;

import org.matchmaker.common.constant.SvcBeatConstant;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Liu Zhongshuai
 * @description svc健康处理器
 * @date 2021-03-17 11:05
 **/
public class SvcHealthHandler implements ApplicationListener<ApplicationReadyEvent> {

    private final SvcInformationContext svcInformationContext;
    private final ScheduledExecutorService svcHealthScheduledExecutor;

    public SvcHealthHandler(SvcInformationContext svcInformationContext) {

        this.svcInformationContext = svcInformationContext;

        this.svcHealthScheduledExecutor = Executors.newScheduledThreadPool(2, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("org.matchmaker.namesvc.health.handler.worker." + UUID.randomUUID().toString());
            return thread;
        });
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        svcHealthScheduledExecutor.scheduleWithFixedDelay(() -> svcInformationContext.getHealthfulSvc().stream().filter(c -> isOffline(c.getRefreshTimestamp())).collect(Collectors.toList()).forEach(c -> svcInformationContext.offline(c.getSvcInfo().getIpAddress())), 2, 2, TimeUnit.SECONDS);
    }

    /**
     * 判断svc是否应该为下线状态
     *
     * @param svcRefreshTimestamp svc 通过心跳产生的刷新时间戳
     * @return tru or false
     */
    private boolean isOffline(Long svcRefreshTimestamp) {
        //判断为非健康=当前时间戳-心跳时间戳>svc心跳时间(5s)+超时时间(2s)
        Long currentTimestamp = System.currentTimeMillis();
        return (currentTimestamp - svcRefreshTimestamp) > (SvcBeatConstant.SVC_DEFAULT_BEAT_INTERVAL + SvcBeatConstant.SVC_DEFAULT_BEAT_OUT_TIME);
    }
}
