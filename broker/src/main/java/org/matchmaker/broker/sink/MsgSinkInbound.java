package org.matchmaker.broker.sink;

import java.util.function.Consumer;

/**
 * @author Liu Zhongshuai
 * @description 消息下行sink接口
 * @date 2021-04-03 13:19
 **/
public interface MsgSinkInbound extends MsgSink {


    /**
     * 接收消息
     *
     * @param consumer 消费逻辑
     */
    void accept(Consumer consumer);
}
