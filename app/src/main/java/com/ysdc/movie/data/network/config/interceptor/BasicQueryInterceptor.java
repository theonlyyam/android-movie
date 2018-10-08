package com.ysdc.movie.data.network.config.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp interceptor to add query parameter to all our queries
 */
public class BasicQueryInterceptor implements Interceptor {

    private final String keyParameterName;
    private final String keyParameterValue;

    public BasicQueryInterceptor(String keyParameterName, String keyParameterValue) {
        this.keyParameterName = keyParameterName;
        this.keyParameterValue = keyParameterValue;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl url = original.url().newBuilder().addQueryParameter(keyParameterName,keyParameterValue).build();
        Request request = original.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
