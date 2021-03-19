package org.matchmaker.common.beat;

import org.matchmaker.common.payload.BaseSvcInfo;

/**
 * @author Liu Zhongshuai
 * @description
 * @date 2021-03-19 11:12
 **/
@FunctionalInterface
public interface DynamicSvcInfoInterface {

    /**
     * 获取svc服务信息
     *
     * @return {@link BaseSvcInfo}
     */
    BaseSvcInfo getSvcInfo();
}
