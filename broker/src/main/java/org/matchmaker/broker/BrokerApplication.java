package org.matchmaker.broker;

import org.matchmaker.broker.listener.BrokerOnApplicationStart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author liuzhongshuai
 */
@SpringBootApplication
public class BrokerApplication {

    public static void main(String[] args) {



        long startTime=System.currentTimeMillis();
         //已使用的内存
        long endTime=System.currentTimeMillis();
        System.out.println("总耗时:"+(endTime-startTime));


        SpringApplication app = new SpringApplication(BrokerApplication.class);
        app.addListeners(new BrokerOnApplicationStart());
        app.run(args);

    }

}
