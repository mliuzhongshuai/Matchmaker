package org.matchmaker.nameserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author liuzhongshuai
 */
@SpringBootApplication
@EnableConfigurationProperties
public class NameServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NameServerApplication.class, args);
    }

}
