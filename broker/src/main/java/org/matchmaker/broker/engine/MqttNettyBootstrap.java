package org.matchmaker.broker.engine;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.matchmaker.broker.engine.codec.ByteBufToWebSocketFrameEncoder;
import org.matchmaker.broker.engine.codec.WebSocketFrameToByteBufDecoder;

/**
 * @author Liu Zhongshuai
 * @description mqtt 服务引擎初始化入口类
 * @date 2021-03-24 18:11
 **/
public class MqttNettyBootstrap implements INettyBootstrap {

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private static final String MQTT_CSV_LIST = "mqtt, mqttv3.1, mqttv3.1.1";


    @Override
    public ServerBootstrap init(BootstrapConfig bootstrapConfig) {
        Class serverSocketClass;

        if (bootstrapConfig.isLinux()) {
            this.bossGroup = new EpollEventLoopGroup(1);
            this.workerGroup = new EpollEventLoopGroup();
            serverSocketClass = EpollServerSocketChannel.class;
        } else {
            this.bossGroup = new NioEventLoopGroup(1);
            this.workerGroup = new NioEventLoopGroup();
            serverSocketClass = NioServerSocketChannel.class;
        }

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.option(ChannelOption.SO_BACKLOG, bootstrapConfig.getSoBacklog());
        b.channel(serverSocketClass);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                initProtocolChannelPipeline(ch.pipeline(), bootstrapConfig);
                ch.pipeline().addLast(new IdleStateHandler(bootstrapConfig.getHeatIdeInterval(), 0, 0));
                //ch.pipeline().addLast()
            }
        });
        return b;
    }

    @Override
    public void shutdown() {
        this.workerGroup.shutdownGracefully();
        this.bossGroup.shutdownGracefully();
    }

    /**
     * 根据不同的协议配置，进行编解器的设置
     *
     * @param bootstrapConfig 配置类
     */
    private void initProtocolChannelPipeline(ChannelPipeline channelPipeline, BootstrapConfig bootstrapConfig) {

        switch (bootstrapConfig.getSupportedProtocol()) {
            case MQTT:
                channelPipeline.addLast("encoder", MqttEncoder.INSTANCE);
                channelPipeline.addLast("decoder", new MqttDecoder());
                break;
            case MQTT_WS:
                channelPipeline.addLast("httpCode", new HttpServerCodec());
                channelPipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                channelPipeline.addLast("webSocketHandler",
                        new WebSocketServerProtocolHandler("/", MQTT_CSV_LIST));
                channelPipeline.addLast("wsDecoder", new WebSocketFrameToByteBufDecoder());
                channelPipeline.addLast("wsEncoder", new ByteBufToWebSocketFrameEncoder());
                channelPipeline.addLast("decoder", new MqttDecoder());
                channelPipeline.addLast("encoder", MqttEncoder.INSTANCE);
                break;
            case MQTT_WS_PAHO:
                channelPipeline.addLast("httpCode", new HttpServerCodec());
                channelPipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                channelPipeline.addLast("webSocketHandler",
                        new WebSocketServerProtocolHandler("/mqtt", MQTT_CSV_LIST));
                channelPipeline.addLast("wsDecoder", new WebSocketFrameToByteBufDecoder());
                channelPipeline.addLast("wsEncoder", new ByteBufToWebSocketFrameEncoder());
                channelPipeline.addLast("decoder", new MqttDecoder());
                channelPipeline.addLast("encoder", MqttEncoder.INSTANCE);
                break;
        }
    }


}
