package com.jtechlib.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.internal.Preconditions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 缓存管理基类
 * Created by jianghan on 2016/9/21.
 */
public abstract class BaseCacheManager {

    private int mode;
    private Gson gson;
    private ACache aCache;
    private Context context;
    private SharedPreferences sharedPreferences;

    public BaseCacheManager(Context context) {
        this(context, Context.MODE_PRIVATE);
    }

    public BaseCacheManager(Context context, int mode) {
        this.context = context;
        this.mode = mode;
    }

    /**
     * 懒加载acache对象
     *
     * @return
     */
    private ACache getACache() {
        Preconditions.checkNotNull(context, "请实例化context构造");
        if (null == aCache) {
            this.aCache = ACache.get(context, getCacheName());
        }
        return aCache;
    }

    /**
     * 懒加载SharedPreferences对象
     *
     * @return
     */
    private SharedPreferences getSharedPreferences() {
        Preconditions.checkNotNull(context, "请实例化context构造");
        if (null == sharedPreferences) {
            this.sharedPreferences = context.getSharedPreferences(getCacheName(), mode);
        }
        return sharedPreferences;
    }

    /**
     * 懒加载Gson对象
     *
     * @return
     */
    private Gson getGson() {
        Preconditions.checkNotNull(context, "请实例化context构造");
        if (null == gson) {//首次获取gson
            this.gson = getCustomizeGson();
            if (null == gson) {//为空则创建默认gson
                this.gson = new Gson();
            }
        }
        return gson;
    }

    /**
     * 插入byte[]类型
     *
     * @param key
     * @param value
     */
    public void put(String key, Byte[] value) {
        put(key, value, -1);
    }

    /**
     * 插入byte[]类型
     *
     * @param key
     * @param value
     */
    public void put(String key, Byte[] value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入string类型
     *
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        put(key, value, -1);
    }

    /**
     * 插入string类型
     *
     * @param key
     * @param value
     */
    public void put(String key, String value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入实现了serializable接口的对象
     *
     * @param key
     * @param value
     * @param <D>
     */
    public <D extends Serializable> void put(String key, D value) {
        put(key, value, -1);
    }

    /**
     * 插入实现了serializable接口的对象
     *
     * @param key
     * @param value
     * @param <D>
     */
    public <D extends Serializable> void put(String key, D value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入jsonobject对象
     *
     * @param key
     * @param value
     */
    public void put(String key, JSONObject value) {
        put(key, value, -1);
    }

    /**
     * 插入jsonobject对象
     *
     * @param key
     * @param value
     */
    public void put(String key, JSONObject value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入jsonarray对象
     *
     * @param key
     * @param value
     */
    public void put(String key, JSONArray value) {
        put(key, value, -1);
    }

    /**
     * 插入jsonarray对象
     *
     * @param key
     * @param value
     */
    public void put(String key, JSONArray value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入一个集合
     *
     * @param key
     * @param values
     * @param <D>
     * @return
     */
    public <D> boolean put(String key, List<D> values) {
        return put(key, values, -1);
    }

    /**
     * 插入一个集合,使用gson解析为jsonarray进行存储，可能会出现很多对象不兼容的问题
     *
     * @param key
     * @param values
     * @param saveTime
     * @param <D>
     * @return
     */
    public <D> boolean put(String key, List<D> values, int saveTime) {
        String json = getGson().toJson(values);
        if (!TextUtils.isEmpty(json)) {
            getACache().put(key, json, saveTime);
        }
        return false;
    }

    /**
     * 插入int类型数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean put(String key, int value) {
        return getSharedPreferences()
                .edit()
                .putInt(key, value)
                .commit();
    }

    /**
     * 插入boolean类型数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean put(String key, boolean value) {
        return getSharedPreferences()
                .edit()
                .putBoolean(key, value)
                .commit();
    }

    /**
     * 插入float类型
     *
     * @param key
     * @param value
     * @return
     */
    public boolean put(String key, float value) {
        return getSharedPreferences()
                .edit()
                .putFloat(key, value)
                .commit();
    }

    /**
     * 插入long类型
     *
     * @param key
     * @param value
     * @return
     */
    public boolean put(String key, long value) {
        return getSharedPreferences()
                .edit()
                .putLong(key, value)
                .commit();
    }

    /**
     * 根据key删除文件
     *
     * @param key
     * @return
     */
    public boolean deleteByKey(@NonNull String key) {
        return getACache().remove(key) || getSharedPreferences()
                .edit()
                .remove(key)
                .commit();
    }

    /**
     * 查byte[]类型
     *
     * @param key
     * @return
     */
    public byte[] getBinary(@NonNull String key) {
        return getACache().getAsBinary(key);
    }

    /**
     * 差String类型
     *
     * @param key
     * @return
     */
    public String getString(@NonNull String key) {
        return getACache().getAsString(key);
    }

    /**
     * 查询实现了Serializable的对象
     *
     * @param key
     * @return
     */
    public <R extends Serializable> R getSerializable(@NonNull String key) {
        return (R) getACache().getAsObject(key);
    }

    /**
     * 查询jsonobject
     *
     * @param key
     * @return
     */
    public JSONObject getJsonObject(@NonNull String key) {
        return getACache().getAsJSONObject(key);
    }

    /**
     * 查询jsonarray
     *
     * @param key
     * @return
     */
    public JSONArray getJsonArray(@NonNull String key) {
        return getACache().getAsJSONArray(key);
    }

    /**
     * 获取一个集合
     *
     * @param key
     * @param <D>
     * @return
     */
    public <D> List<D> getList(@NonNull String key, Type typeOfT) {
        String json = getACache().getAsString(key);
        if (!TextUtils.isEmpty(json)) {
            return getGson().fromJson(json, typeOfT);
        }
        return null;
    }

    /**
     * 查询int
     *
     * @param key
     * @return
     */
    public int getInt(@NonNull String key, int defValut) {
        return getSharedPreferences().getInt(key, defValut);
    }

    /**
     * 查询boolean
     *
     * @param key
     * @return
     */
    public boolean getBoolean(@NonNull String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    /**
     * 查询float
     *
     * @param key
     * @return
     */
    public float getFloat(@NonNull String key, float defValue) {
        return getSharedPreferences().getFloat(key, defValue);
    }

    /**
     * 查询long
     *
     * @param key
     * @return
     */
    public long getLong(@NonNull String key, long defVlaue) {
        return getSharedPreferences().getLong(key, defVlaue);
    }

    /**
     * 获取缓存路径名
     *
     * @return
     */
    public abstract String getCacheName();

    /**
     * 获取定制gson,使用者可以重写,不为空则会使用当前返回
     *
     * @return
     */
    public Gson getCustomizeGson() {
        return new Gson();
    }
}