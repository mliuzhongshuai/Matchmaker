package org.matchmaker.nameserver.configure;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Liu Zhongshuai
 * @description web 容器配置
 * @date 2021-03-15 09:26
 **/
@Configuration
public class WebContainerUndertowConfigure {
    @Bean
    public ServletWebServerFactory servletContainer() {
        UndertowServletWebServerFactory undertow = new UndertowServletWebServerFactory();
        return undertow;
    }
}
