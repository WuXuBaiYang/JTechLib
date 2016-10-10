package com.jtechlib;

import android.app.Application;

import com.karumi.dexter.Dexter;

/**
 * Application基类
 * Created by wuxubaiyang on 16/4/21.
 */
public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化权限检查库
        Dexter.initialize(getApplicationContext());
    }
}