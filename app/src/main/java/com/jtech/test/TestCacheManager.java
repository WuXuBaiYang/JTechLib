package com.jtech.test;

import android.content.Context;

import com.jtechlib.cache.BaseCacheManager;


/**
 * 缓存管理样例
 * Created by jianghan on 2016/9/21.
 */
public class TestCacheManager extends BaseCacheManager {
    private static TestCacheManager testCacheManager;

    public TestCacheManager(Context context) {
        super(context);
    }

    @Override
    public String getCacheName() {
        return "testCacheManager";
    }

    public static TestCacheManager get(Context context) {
        if (null == testCacheManager) {
            testCacheManager = new TestCacheManager(context);
        }
        return testCacheManager;
    }
}