package org.matchmaker.broker.sink;

import java.util.List;

/**
 * @author Liu Zhongshuai
 * @description 发送回调接口
 * @date 2021-04-04 09:21
 **/
@FunctionalInterface
public interface SendCallback {


    /**
     * 发送回调业务逻辑处理器
     *
     * @param success 消息是否发送成功
     */
    void handler(List message, Boolean success);
}
