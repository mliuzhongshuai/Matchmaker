package org.matchmaker.nameserver.core;

import org.matchmaker.common.enums.SvcRole;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Liu Zhongshuai
 * @description 服务信息上下文 包含各角色的服务上报的信息
 * @date 2021-03-16 16:08
 **/
public class SvcInformationContext {

    private SvcInformationContext() {
    }

    private static SvcInformationContext svcInformationContext = new SvcInformationContext();

    /**
     * address mapping SvcMateInformation
     */
    private volatile static Map<String, SvcMateInformation> addressSvcMapping = new ConcurrentHashMap<>(6);


    public static SvcInformationContext singletonInstantiate() {
        return svcInformationContext;
    }

    /**
     * svc 新注册、刷新
     *
     * @param svcMateInformation 包含了健康信息的 svc
     */
    public void registerOrRefresh(SvcMateInformation svcMateInformation) {
        addressSvcMapping.put(svcMateInformation.getSvcInfo().getIpAddress(), svcMateInformation);
    }

    /**
     * 获取 svc 组信息
     *
     * @param svcRole svc 角色名称
     * @return {@link List<SvcMateInformation>}
     */
    public List<SvcMateInformation> getAllSvcByRole(SvcRole svcRole) {
        return addressSvcMapping.values().stream().filter(c -> c.getSvcInfo().getSvcRole().equals(svcRole)).collect(Collectors.toList());
    }


    /**
     * 获取健康的svc组
     *
     * @return {@link List<SvcMateInformation>}
     */
    public List<SvcMateInformation> getHealthfulSvc() {
        return addressSvcMapping.values().stream().filter(SvcMateInformation::isUp).collect(Collectors.toList());
    }


    /**
     * 获取健康的svc组
     *
     * @param svcRole svc 角色
     * @return {@link List<SvcMateInformation>}
     */
    public List<SvcMateInformation> getHealthfulSvcByRole(SvcRole svcRole) {
        return addressSvcMapping.values().stream().filter(c -> c.getSvcInfo().getSvcRole().equals(svcRole) && c.isUp()).collect(Collectors.toList());
    }

    /**
     * 标识服务下线
     *
     * @param address ip:port
     */
    public void offline(String address) {
        Optional.ofNullable(addressSvcMapping.get(address)).ifPresent(c -> c.setUp(Boolean.FALSE));
    }


}
