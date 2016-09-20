package com.jtechlib.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.jtechlib.view.widget.ToolbarChain;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.ButterKnife;

/**
 * activity基类
 * Created by wuxubaiyang on 16/4/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将当前activity添加到activity管理中
        ActivityManager.getInstance().addActivity(this);
        //初始化变量(用户传递进来的参数)
        initVariables(getBundle());
        //初始化视图
        initViews(savedInstanceState);
        //加载数据
        loadData();
    }

    /**
     * 初始化变量
     *
     * @param bundle
     */
    protected abstract void initVariables(Bundle bundle);

    /**
     * 初始化视图
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 请求数据
     */
    protected abstract void loadData();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        //绑定注解
        ButterKnife.bind(getActivity());
    }

    /**
     * 设置toolbar
     *
     * @param toolbar
     * @return
     */
    public ToolbarChain setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        return new ToolbarChain(getActivity(), toolbar);
    }

    /**
     * 获取activity对象
     *
     * @return
     */
    public BaseActivity getActivity() {
        return this;
    }

    /**
     * 获取传递进来的参数
     *
     * @return
     */
    public Bundle getBundle() {
        Intent intent = getIntent();
        if (null != intent) {
            return intent.getExtras();
        }
        return null;
    }

    /**
     * 权限检查 单个
     *
     * @param permission
     * @param permissionListener
     */
    public void checkPermission(PermissionListener permissionListener, String permission) {
        Dexter.checkPermission(permissionListener, permission);
    }

    /**
     * 权限检查 多个
     *
     * @param multiplePermissionsListener
     * @param permissions
     */
    public void checkPermission(MultiplePermissionsListener multiplePermissionsListener, String... permissions) {
        Dexter.checkPermissions(multiplePermissionsListener, permissions);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean onKey = true;
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                keyBack();
                break;
            default:
                onKey = super.onKeyDown(keyCode, event);
                break;
        }
        return onKey;
    }

    /**
     * 响应后退按键
     */
    public void keyBack() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除当前activity对象
        ActivityManager.getInstance().removeActivity(this);
    }
}