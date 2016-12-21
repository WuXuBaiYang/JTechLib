package com.jtechlib.cache;

import android.content.Context;
import android.content.SharedPreferences;
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

    private Context context;

    private int mode;
    private ACache aCache;
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
     * 插入数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean insert(@NonNull String key, @NonNull Object value) {
        return insert(key, value, -1);
    }

    /**
     * 插入数据
     *
     * @param key      存储Key
     * @param value    类型限制：byte[]/String/serializable/jsonobject/jsonarray
     * @param saveTime 存储时间，单位（秒）
     * @return 是否插入成功
     */
    public boolean insert(@NonNull String key, @NonNull Object value, int saveTime) {
        String clazzType = value.getClass().getName();
        if (value instanceof Byte[]) {//为byte[]类型
            insertByte(key, (Byte[]) value, saveTime);
        } else if (value instanceof String) {//为String类型
            insertString(key, (String) value, saveTime);
        } else if (value instanceof JSONObject) {//jsonobject对象
            insertJsonObject(key, (JSONObject) value, saveTime);
        } else if (value instanceof JSONArray) {//jsonarray对象
            insertJsonArray(key, (JSONArray) value, saveTime);
        } else if (value instanceof Integer || "int".equals(clazzType)) {//是否为int或者int封装类型
            return insertInt(key, (Integer) value);
        } else if (value instanceof Boolean || "boolean".equals(clazzType)) {//是否为boolean或者boolean封装类型
            return insertBoolean(key, (Boolean) value);
        } else if (value instanceof Float || "float".equals(clazzType)) {//是否为float或者float封装类型
            return insertFloat(key, (Float) value);
        } else if (value instanceof Long || "long".equals(clazzType)) {//是否为long或者long封装类型
            return insertLong(key, (Long) value);
        } else if (value instanceof Serializable) {//实现了序列化接口
            insertSerializable(key, (Serializable) value, saveTime);
        } else {
            return false;
        }
        return true;
    }

    /**
     * 插入byte[]类型
     *
     * @param key
     * @param value
     */
    public void insertByte(String key, Byte[] value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入byte[]类型
     *
     * @param key
     * @param value
     */
    public void insertByte(String key, Byte[] value) {
        insertByte(key, value, -1);
    }

    /**
     * 插入string类型
     *
     * @param key
     * @param value
     */
    public void insertString(String key, String value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入string类型
     *
     * @param key
     * @param value
     */
    public void insertString(String key, String value) {
        insertString(key, value, -1);
    }

    /**
     * 插入实现了serializable接口的对象
     *
     * @param key
     * @param value
     * @param <D>
     */
    public <D extends Serializable> void insertSerializable(String key, D value, int saveTime) {
        getACache().put(key, value, saveTime);
    }


    /**
     * 插入实现了serializable接口的对象
     *
     * @param key
     * @param value
     * @param <D>
     */
    public <D extends Serializable> void insertSerializable(String key, D value) {
        getACache().put(key, value, -1);
    }

    /**
     * 插入jsonobject对象
     *
     * @param key
     * @param value
     */
    public void insertJsonObject(String key, JSONObject value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入jsonobject对象
     *
     * @param key
     * @param value
     */
    public void insertJsonObject(String key, JSONObject value) {
        getACache().put(key, value, -1);
    }

    /**
     * 插入jsonarray对象
     *
     * @param key
     * @param value
     */
    public void insertJsonArray(String key, JSONArray value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入jsonarray对象
     *
     * @param key
     * @param value
     */
    public void insertJsonArray(String key, JSONArray value) {
        getACache().put(key, value, -1);
    }

    /**
     * 插入int类型数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean insertInt(String key, int value) {
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
    public boolean insertBoolean(String key, boolean value) {
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
    public boolean insertFloat(String key, float value) {
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
    public boolean insertLong(String key, long value) {
        return getSharedPreferences()
                .edit()
                .putLong(key, value)
                .commit();
    }

    /**
     * 删除文件
     *
     * @param key
     * @return
     */
    public boolean delete(@NonNull String key) {
        return getACache().remove(key) || getSharedPreferences()
                .edit()
                .remove(key)
                .commit();
    }

    /**
     * 更新数据
     *
     * @param key
     * @param newValue
     * @return
     */
    public boolean update(@NonNull String key, @NonNull Object newValue) {
        return update(key, newValue, -1);
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
        return insert(key, newValue, saveTime);
    }

    /**
     * 查byte[]类型
     *
     * @param key
     * @return
     */
    public byte[] queryBinary(@NonNull String key) {
        return getACache().getAsBinary(key);
    }

    /**
     * 差String类型
     *
     * @param key
     * @return
     */
    public String queryString(@NonNull String key) {
        return getACache().getAsString(key);
    }

    /**
     * 查询实现了Serializable的对象
     *
     * @param key
     * @return
     */
    public <R> R querySerializable(@NonNull String key) {
        return (R) getACache().getAsObject(key);
    }

    /**
     * 查询jsonobject
     *
     * @param key
     * @return
     */
    public JSONObject queryJsonObject(@NonNull String key) {
        return getACache().getAsJSONObject(key);
    }

    /**
     * 查询jsonarray
     *
     * @param key
     * @return
     */
    public JSONArray queryJsonArray(@NonNull String key) {
        return getACache().getAsJSONArray(key);
    }

    /**
     * 查询int
     *
     * @param key
     * @return
     */
    public int queryInt(@NonNull String key, int defValut) {
        return getSharedPreferences().getInt(key, defValut);
    }

    /**
     * 查询boolean
     *
     * @param key
     * @return
     */
    public boolean queryBoolean(@NonNull String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    /**
     * 查询float
     *
     * @param key
     * @return
     */
    public float queryFloat(@NonNull String key, float defValue) {
        return getSharedPreferences().getFloat(key, defValue);
    }

    /**
     * 查询long
     *
     * @param key
     * @return
     */
    public long queryLong(@NonNull String key, long defVlaue) {
        return getSharedPreferences().getLong(key, defVlaue);
    }

    /**
     * 获取缓存路径名
     *
     * @return
     */
    public abstract String getCacheName();
}