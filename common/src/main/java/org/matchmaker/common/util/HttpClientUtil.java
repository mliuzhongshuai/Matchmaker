package org.matchmaker.common.util;

import okhttp3.*;
import org.matchmaker.common.exception.HttpRequestException;

import java.util.concurrent.TimeUnit;

/**
 * @author Liu Zhongshuai
 * @description http client util
 * @date 2021-03-19 09:37
 **/
public class HttpClientUtil {

    private HttpClientUtil() {
    }

    private final static OkHttpClient okHttpClient;

    private final static MediaType JSON_MEDIA = MediaType.get("application/json; charset=utf-8");

    static {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.SECONDS)
                .build();
    }

    /**
     * a post request
     *
     * @param url     请求地址
     * @param jsonStr 请求报文仅支持(application/json)
     */
    public static String post(String url, String jsonStr) throws HttpRequestException {

        RequestBody body = RequestBody.create(jsonStr, JSON_MEDIA);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            final int successCode = 200;
            if (successCode != response.code()) {
                throw new HttpRequestException(null);
            }
            return response.body().string();
        } catch (Exception e) {
            throw new HttpRequestException("Http request error,the params:" + jsonStr);
        }
    }

}
