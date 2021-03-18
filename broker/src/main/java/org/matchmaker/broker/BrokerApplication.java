package org.matchmaker.broker;

import org.matchmaker.broker.listener.BrokerOnApplicationStart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuzhongshuai
 */
@SpringBootApplication
public class BrokerApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(BrokerApplication.class);
        app.addListeners(new BrokerOnApplicationStart());
        app.run(args);

    }

}
