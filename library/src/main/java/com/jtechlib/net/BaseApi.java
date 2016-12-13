package com.jtechlib.net;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * api基类
 * Created by jianghan on 2016/9/19.
 */
public class BaseApi {
    /**
     * 创建一个接口方法
     *
     * @param okHttpClient
     * @param converterFactory
     * @param callAdapterFactory
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(OkHttpClient okHttpClient, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, String baseUrl, Class<T> service) {
        Retrofit.Builder builder = new Retrofit.Builder()
                //基础url
                .baseUrl(baseUrl)
                //客户端OKHttp
                .client(okHttpClient);
        //添加转换工厂
        if (null != converterFactory) {
            builder.addConverterFactory(converterFactory);
        }
        //添加请求工厂
        if (null != callAdapterFactory) {
            builder.addCallAdapterFactory(callAdapterFactory);
        }
        //创建retrofit对象
        Retrofit retrofit = builder.build();
        //返回创建的api
        return retrofit.create(service);
    }

    public <T> T createApi(Interceptor interceptor, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, String baseUrl, Class<T> service) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (null != interceptor) {
            builder.addInterceptor(interceptor);
        }
        return create(builder.build(), converterFactory, callAdapterFactory, baseUrl, service);
    }

    public <T> T createApi(Map<String, String> headerMap, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, String baseUrl, Class<T> service) {
        return createApi(new CommonInterceptor(headerMap), converterFactory, callAdapterFactory, baseUrl, service);
    }

    public <T> T createApi(Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, String baseUrl, Class<T> service) {
        return createApi(new HashMap<String, String>(), converterFactory, callAdapterFactory, baseUrl, service);
    }

    public <T> T createApi(CallAdapter.Factory callAdapterFactory, String baseUrl, Class<T> service) {
        return createApi(null, callAdapterFactory, baseUrl, service);
    }

    public <T> T createApi(String baseUrl, Class<T> service) {
        return createApi(null, baseUrl, service);
    }

    public <T> T createRxApi(Interceptor interceptor, Converter.Factory converterFactory, String baseUrl, Class<T> service) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (null != interceptor) {
            builder.addInterceptor(interceptor);
        }
        return create(builder.build(), converterFactory, RxJavaCallAdapterFactory.create(), baseUrl, service);
    }

    public <T> T createRxApi(Map<String, String> headerMap, Converter.Factory converterFactory, String baseUrl, Class<T> service) {
        return createRxApi(new CommonInterceptor(headerMap), converterFactory, baseUrl, service);
    }

    public <T> T createRxApi(Converter.Factory converterFactory, String baseUrl, Class<T> service) {
        return createRxApi(new HashMap<String, String>(), converterFactory, baseUrl, service);
    }

    public <T> T createRxApi(String baseUrl, Class<T> service) {
        return createRxApi(null, baseUrl, service);
    }
}