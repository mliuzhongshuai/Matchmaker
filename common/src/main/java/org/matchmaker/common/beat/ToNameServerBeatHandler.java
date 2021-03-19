package org.matchmaker.common.beat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.matchmaker.common.constant.NameServerUriConstant;
import org.matchmaker.common.constant.SvcBeatConstant;
import org.matchmaker.common.payload.BaseSvcInfo;
import org.matchmaker.common.util.HttpClientUtil;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Liu Zhongshuai
 * @description nameserver beat handler
 * @date 2021-03-15 18:24
 **/
@Slf4j
public class ToNameServerBeatHandler {

    private final int beatInterval;
    private final NameServerInfo nameServerInfo;
    private final DynamicSvcInfoInterface dynamicSvcInfoInterface;
    private final ScheduledExecutorService scheduledExecutorService;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    public ToNameServerBeatHandler(NameServerInfo nameServerInfo, DynamicSvcInfoInterface dynamicSvcInfoInterface, Integer interval) {

        if (null == nameServerInfo || null == nameServerInfo.getIpAddress() || nameServerInfo.getIpAddress().size() == 0) {
            throw new IllegalArgumentException("Name server params error!");
        }

        int threadNum = nameServerInfo.getIpAddress().size();
        int coreNum = Runtime.getRuntime().availableProcessors();

        threadNum = threadNum > coreNum ? coreNum : threadNum;

        this.scheduledExecutorService = Executors.newScheduledThreadPool(threadNum, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("org.matchmaker.svc.beat.worker." + UUID.randomUUID().toString());
            return thread;
        });

        this.beatInterval = null == interval ? SvcBeatConstant.SVC_DEFAULT_BEAT_INTERVAL : interval;
        this.nameServerInfo = nameServerInfo;
        this.dynamicSvcInfoInterface = dynamicSvcInfoInterface;
    }

    /**
     * 启动心跳续约
     */
    public void beatStart() {

        nameServerInfo.getIpAddress().forEach(domain -> {

            String beatUrl = domain + NameServerUriConstant.SVC_REGISTER_URI;

            scheduledExecutorService.scheduleWithFixedDelay(() -> {

                BaseSvcInfo baseSvcInfo = dynamicSvcInfoInterface.getSvcInfo();

                try {
                    HttpClientUtil.post(beatUrl, objectMapper.writeValueAsString(baseSvcInfo));
                } catch (Exception e) {
                    log.error("Beat request error,requestBody:{},error msg:", baseSvcInfo.toString(), e);
                }
            }, 2, beatInterval, TimeUnit.SECONDS);
        });
    }

}
