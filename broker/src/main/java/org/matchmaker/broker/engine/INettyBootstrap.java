package org.matchmaker.broker.engine;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;

/**
 * @author Liu Zhongshuai
 * @description netty 启动类接口
 * @date 2021-03-24 18:15
 **/
public interface INettyBootstrap {


    /**
     * 初始化netty serverBootstrap
     *
     * @param bootstrapConfig 配置类
     * @return {@link ServerBootstrap}
     */
    ServerBootstrap init(BootstrapConfig bootstrapConfig);


    /**
     * 关闭服务，回收相关资源
     */
    void shutdown();


    /**
     * 启动服务
     *
     * @param bootstrapConfig 配置类
     */
    default void start(BootstrapConfig bootstrapConfig) {
        try {
            ChannelFuture f = init(bootstrapConfig).bind(bootstrapConfig.getPort()).sync();
            System.out.println("Broker initiated...");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

}
