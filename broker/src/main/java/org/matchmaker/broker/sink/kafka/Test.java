package org.matchmaker.broker.sink.kafka;

import org.matchmaker.broker.sink.MsgSinkOutbound;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liu Zhongshuai
 * @description
 * @date 2021-04-04 09:38
 **/
public class Test {

    public static void main(String[] args) {
        MsgSinkOutbound sinkOutbound=new KafkaSink();
        List<String> list1 = Arrays.asList("asdf");


        sinkOutbound.asynPush(list1,(a,b)->{
                list1.stream().collect(Collectors.toList());
        });
    }
}
