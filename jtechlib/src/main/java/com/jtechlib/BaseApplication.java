package com.jtechlib;

import android.app.Application;

import com.karumi.dexter.Dexter;

/**
 * Application基类
 * Created by wuxubaiyang on 16/4/21.
 */
public abstract class BaseApplication extends Application {

    private static BaseApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        //赋值instance
        this.INSTANCE = this;
        //初始化权限检查库
        Dexter.initialize(getApplicationContext());
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return INSTANCE;
    }
}