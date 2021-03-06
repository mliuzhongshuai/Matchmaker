package org.matchmaker.broker.engine;

import java.util.List;

/**
 * @author Liu Zhongshuai
 * @description 消息上行sink接口
 * @date 2021-04-03 13:19
 **/
public interface MsgSinkOutbound extends MsgSink {

    /**
     * 向 sink 推送消息
     *
     * @param messages 消息集合
     * @return 推送是否完成(true or false)
     */
    boolean push(List messages);

    /**
     * 异步推送
     *
     * @param message      推送的消息
     * @param pushCallback 推送完成逻辑
     */
    void asynPush(List message, PushCallback pushCallback);

}
