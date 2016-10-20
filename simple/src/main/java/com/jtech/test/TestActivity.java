package com.jtech.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.jakewharton.rxbinding.view.RxView;
import com.jtechlib.view.activity.BaseActivity;

import butterknife.Bind;
import rx.functions.Action1;


/**
 * 测试用activity
 * Created by jianghan on 2016/9/20.
 */
public class TestActivity extends BaseActivity {

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void initVariables(Bundle bundle) {
        //初始化参数变量
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //初始化视图
        setContentView(R.layout.activity_test);

        RxView.clicks(fab).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                jumpTo(TestActivity2.class)
                        .makeSceneTransitionAnimation()
                        .addPairs(fab, "fab")
                        .jump();
            }
        });
    }

    @Override
    protected void loadData() {
        //请求数据

    }
}