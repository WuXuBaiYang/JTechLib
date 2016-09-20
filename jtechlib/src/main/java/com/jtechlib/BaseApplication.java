package com.jtechlib;

import android.app.Application;

import com.karumi.dexter.Dexter;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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

    /**
     * 初始化realm
     */
    public Realm getRealm() {
        return Realm.getInstance(getRealmConfiguration());
    }

    /**
     * 获取数据库配置信息
     *
     * @return
     */
    private RealmConfiguration getRealmConfiguration() {
        return new RealmConfiguration
                .Builder(getApplicationContext())
                .name(getDBName())
                .build();
    }

    /**
     * 获取数据库名称
     *
     * @return
     */
    protected abstract String getDBName();
}