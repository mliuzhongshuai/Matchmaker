package org.matchmaker.common.exception;

/**
 * @author Liu Zhongshuai
 * @description 自定义异常:心跳请求异常
 * @date 2021-03-19 10:06
 **/
public class HttpRequestException extends Exception{

    public HttpRequestException(String message){
        super(message);
    }
}
