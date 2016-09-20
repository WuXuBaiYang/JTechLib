package com.jtechlib.net;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 通用拦截器
 */
class CommonInterceptor implements Interceptor {
    private Map<String, String> headerMap;

    public CommonInterceptor(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        //拼接请求，添加头
        Request.Builder builder = chain.request()
                .newBuilder();
        if (null != headerMap) {
            Iterator<String> iterator = headerMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = headerMap.get(key);
                builder.addHeader(key, value);
            }
        }
        return chain.proceed(builder.build());
    }
}