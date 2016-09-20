package com.jtech.test;

import com.jtechlib.BaseApplication;

/**
 * 测试实现方法
 * Created by jianghan on 2016/9/20.
 */
public class TestApplication extends BaseApplication {
    @Override
    protected String getDBName() {
        return "TestDb";
    }
}