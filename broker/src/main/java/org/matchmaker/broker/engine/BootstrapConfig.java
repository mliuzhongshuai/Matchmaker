package org.matchmaker.broker.engine;

import lombok.Getter;
import org.matchmaker.broker.configure.BrokerProperties;
import org.matchmaker.common.enums.BrokerServerSupportProtocol;

/**
 * @author Liu Zhongshuai
 * @description bs 配置bean
 * @date 2021-03-24 18:34
 **/
public class BootstrapConfig {

    public BootstrapConfig() {

    }


    @Getter
    private int port;

    @Getter
    private int soBacklog;

    private Os currentOsName;

    @Getter
    private int heatIdeInterval;

    @Getter
    private BrokerServerSupportProtocol supportedProtocol;

    public boolean isLinux() {
        return this.currentOsName.equals(Os.LINUX);
    }


    private void init(BrokerProperties brokerProperties) {
        this.currentOsName = getCurrentOs();
    }


    private Os getCurrentOs() {
        String osName = System.getProperty("os.name");

        if (null == osName) {
            return Os.OTHER;
        }

        if (osName.startsWith("Linux")) {
            return Os.LINUX;
        } else if (osName.startsWith("Windows")) {
            return Os.WINDOWS;
        } else if (osName.startsWith("Mac")) {
            return Os.OSX;
        } else {
            return Os.OTHER;
        }
    }


    /**
     * 操作系统枚举
     */
    private enum Os {

        LINUX,
        WINDOWS,
        OSX,
        OTHER;

        Os() {
        }
    }


}
