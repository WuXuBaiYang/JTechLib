package com.jtechlib.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jakewharton.rxbinding.internal.Preconditions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 缓存管理基类
 * Created by jianghan on 2016/9/21.
 */
public abstract class BaseCacheManager {

    private ACache aCache;

    public BaseCacheManager(Context context) {
        aCache = ACache.get(context, getCacheName());
    }

    /**
     * * 插入数据
     *
     * @param key      存储Key
     * @param value    类型限制：byte[]/String/serializable/jsonobject/jsonarray
     * @param saveTime 存储时间，单位（秒）
     * @return 是否插入成功
     */
    public boolean insert(@NonNull String key, @NonNull Object value, int saveTime) {
        Preconditions.checkNotNull(aCache, "请实例化context构造");
        if (value instanceof Byte[]) {
            aCache.put(key, (byte[]) value, saveTime);
        } else if (value instanceof String) {
            aCache.put(key, (String) value, saveTime);
        } else if (value instanceof Serializable) {
            aCache.put(key, (Serializable) value, saveTime);
        } else if (value instanceof JSONObject) {
            aCache.put(key, (JSONObject) value, saveTime);
        } else if (value instanceof JSONArray) {
            aCache.put(key, (JSONArray) value, saveTime);
        } else {
            return false;
        }
        return true;
    }

    /**
     * 封装方法
     *
     * @param key
     * @param value
     * @return
     */
    public boolean insert(@NonNull String key, @NonNull Object value) {
        return insert(key, value, -1);
    }

    /**
     * 删除文件
     *
     * @param key
     * @return
     */
    public boolean delete(@NonNull String key) {
        Preconditions.checkNotNull(aCache, "请实例化context构造");
        return aCache.remove(key);
    }

    /**
     * 更新数据
     *
     * @param key
     * @param newValue
     * @param saveTime
     * @return
     */
    public boolean update(@NonNull String key, @NonNull Object newValue, int saveTime) {
        Preconditions.checkNotNull(aCache, "请实例化context构造");
        //使用新数据覆盖已有数据
        return insert(key, newValue, saveTime);
    }

    /**
     * 封装方法
     *
     * @param key
     * @param newValue
     * @return
     */
    public boolean update(@NonNull String key, @NonNull Object newValue) {
        return update(key, newValue, -1);
    }

    /**
     * 根据key查询数据
     *
     * @param key
     * @param <R>
     * @return
     */
    private <R> R query(@NonNull String key, @NonNull Class<?> clazz) {
        Preconditions.checkNotNull(aCache, "请实例化context构造");
        if (clazz == Byte[].class) {
            return (R) aCache.getAsBinary(key);
        } else if (clazz == String.class) {
            return (R) aCache.getAsString(key);
        } else if (clazz == Serializable.class) {
            return (R) aCache.getAsObject(key);
        } else if (clazz == JSONObject.class) {
            return (R) aCache.getAsJSONObject(key);
        } else if (clazz == JSONArray.class) {
            return (R) aCache.getAsJSONArray(key);
        }
        return null;
    }

    /**
     * 查byte[]类型
     *
     * @param key
     * @return
     */
    public Byte[] queryBinary(@NonNull String key) {
        return query(key, Byte[].class);
    }

    /**
     * 差String类型
     *
     * @param key
     * @return
     */
    public String queryString(@NonNull String key) {
        return query(key, String.class);
    }

    /**
     * 查询实现了Serializable的对象
     *
     * @param key
     * @return
     */
    public <R> R queryObject(@NonNull String key) {
        return query(key, Serializable.class);
    }

    /**
     * 查询jsonobject
     *
     * @param key
     * @return
     */
    public JSONObject queryJsonObject(@NonNull String key) {
        return query(key, JSONObject.class);
    }

    /**
     * 查询jsonarray
     *
     * @param key
     * @return
     */
    public JSONArray queryJsonArray(@NonNull String key) {
        return query(key, JSONArray.class);
    }

    /**
     * 获取缓存路径名
     *
     * @return
     */
    public abstract String getCacheName();
}