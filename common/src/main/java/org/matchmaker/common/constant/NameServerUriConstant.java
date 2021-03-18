package org.matchmaker.common.constant;

/**
 * @author Liu Zhongshuai
 * @description name-server 服务相关api uri
 * @date 2021-03-15 16:40
 **/
public class NameServerUriConstant {

    /**
     * 服务注册到 nameserverui 的接口
     */
    public static final String SVC_REGISTER_URI = "/v0/svc/beat";
    /**
     * 获取健康的 svc list
     */
    public static final String SVC_HEALTHFUL_URI = "/v0/svc/{role}/healthful/list";


}
