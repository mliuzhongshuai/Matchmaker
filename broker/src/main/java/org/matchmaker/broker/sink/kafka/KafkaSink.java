package org.matchmaker.broker.sink.kafka;

import org.matchmaker.broker.engine.PushCallback;
import org.matchmaker.broker.engine.MsgSinkInbound;
import org.matchmaker.broker.engine.MsgSinkOutbound;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Liu Zhongshuai
 * @description sink 具体实现
 * @date 2021-04-03 13:38
 **/
public class KafkaSink implements MsgSinkInbound, MsgSinkOutbound {
    @Override
    public boolean push(List messages) {
        return false;
    }

    @Override
    public void asynPush(List message, PushCallback pushCallback) {
        pushCallback.handler(message,true);
    }

    @Override
    public void accept(Consumer consumer) {
        //得到消息
        consumer.accept(null);
    }
}
