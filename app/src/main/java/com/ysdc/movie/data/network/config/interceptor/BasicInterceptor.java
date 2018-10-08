package com.ysdc.movie.data.network.config.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicInterceptor implements Interceptor {

    private final String keyParameterName;
    private final String keyParameterValue;

    public BasicInterceptor(String keyParameterName, String keyParameterValue) {
        this.keyParameterName = keyParameterName;
        this.keyParameterValue = keyParameterValue;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header(keyParameterName,keyParameterValue);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
