package org.matchmaker.common.beat;

import org.matchmaker.common.constant.SvcBeatConstant;
import org.matchmaker.common.payload.BaseSvcInfo;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Liu Zhongshuai
 * @description nameserver beat handler
 * @date 2021-03-15 18:24
 **/
public class ToNameServerBeatHandler {

    private final int beatInterval;
    private final BaseSvcInfo baseSvcInfo;
    private final NameServerInfo nameServerInfo;
    private final ScheduledExecutorService scheduledExecutorService;

    public ToNameServerBeatHandler(NameServerInfo nameServerInfo, BaseSvcInfo baseSvcInfo, Integer interval) {

        if (null == nameServerInfo || null == nameServerInfo.getIpAddress() || nameServerInfo.getIpAddress().size() == 0) {
            throw new IllegalArgumentException("Name server params error!");
        }

        int threadNum = nameServerInfo.getIpAddress().size();
        int coreNum = Runtime.getRuntime().availableProcessors();

        threadNum = threadNum > coreNum ? coreNum : threadNum;

        this.scheduledExecutorService = Executors.newScheduledThreadPool(threadNum, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("org.matchmaker." + baseSvcInfo.getSvcRole() + ".beat.worker." + UUID.randomUUID().toString());
            return thread;
        });

        this.beatInterval = null == interval ? SvcBeatConstant.SVC_DEFAULT_BEAT_INTERVAL : interval;
        this.baseSvcInfo = baseSvcInfo;
        this.nameServerInfo = nameServerInfo;
    }

    /**
     * 启动心跳续约
     */
    public void beatStart() {
        nameServerInfo.getIpAddress().forEach(c -> {
            scheduledExecutorService.scheduleWithFixedDelay(() -> {
                //TODO 推送到nameserver
            }, 2, beatInterval, TimeUnit.SECONDS);
        });
    }

}
