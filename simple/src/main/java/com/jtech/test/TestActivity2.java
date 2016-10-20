package com.jtech.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.jtechlib.view.activity.BaseActivity;

import butterknife.Bind;

/**
 * 页面跳转测试
 * Created by jianghan on 2016/10/20.
 */

public class TestActivity2 extends BaseActivity {

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void initVariables(Bundle bundle) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test2);
    }

    @Override
    protected void loadData() {

    }
}