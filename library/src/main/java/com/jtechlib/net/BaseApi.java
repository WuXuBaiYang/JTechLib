package com.jtechlib.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * api基类
 * Created by jianghan on 2016/9/19.
 */
public class BaseApi {
    /**
     * gson对象,通用
     */
    public static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .create();

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
    public <T> T createApi(OkHttpClient okHttpClient, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory, String baseUrl, Class<T> service) {
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

    /**
     * 创建一个接口方法
     *
     * @param okHttpClient
     * @param callAdapterFactory
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createApi(OkHttpClient okHttpClient, CallAdapter.Factory callAdapterFactory, String baseUrl, Class<T> service) {
        return createApi(okHttpClient, GsonConverterFactory.create(gson), callAdapterFactory, baseUrl, service);
    }

    /**
     * 创建一个接口方法
     *
     * @param interceptor
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createApi(Interceptor interceptor, String baseUrl, Class<T> service) {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(interceptor)
                .build();
        return createApi(okHttpClient, GsonConverterFactory.create(gson), null, baseUrl, service);
    }

    /**
     * 创建一个接口方法
     *
     * @param headerMap
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createApi(Map<String, String> headerMap, String baseUrl, Class<T> service) {
        return createApi(new CommonInterceptor(headerMap), baseUrl, service);
    }

    /**
     * 创建一个接口方法
     *
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createApi(String baseUrl, Class<T> service) {
        return createApi(new CommonInterceptor(new HashMap<String, String>()), baseUrl, service);
    }

    /**
     * 创建一个接口方法
     *
     * @param interceptor
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createRxApi(Interceptor interceptor, String baseUrl, Class<T> service) {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(interceptor)
                .build();
        return createApi(okHttpClient, RxJavaCallAdapterFactory.create(), baseUrl, service);
    }

    /**
     * @param headerMap
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createRxApi(Map<String, String> headerMap, String baseUrl, Class<T> service) {
        return createRxApi(new CommonInterceptor(headerMap), baseUrl, service);
    }

    /**
     * 创建一个接口方法
     *
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createRxApi(String baseUrl, Class<T> service) {
        return createRxApi(new CommonInterceptor(new HashMap<String, String>()), baseUrl, service);
    }
}