package com.jtech.test;

import android.os.Bundle;

import com.jtechlib.view.activity.BaseActivity;


/**
 * 测试用activity
 * Created by jianghan on 2016/9/20.
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void initVariables(Bundle bundle) {
        //初始化参数变量
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //初始化视图
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void loadData() {
        //请求数据
    }
}