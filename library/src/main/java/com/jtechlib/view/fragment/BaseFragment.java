package com.jtechlib.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * Created by wuxubaiyang on 16/4/16.
 */
public abstract class BaseFragment extends Fragment {

    public static String TAG = BaseFragment.class.getSimpleName();
    private View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == contentView) {
            //赋值TAG
            TAG = this.getClass().getSimpleName();
            //创建视图
            contentView = createView(inflater, container);
            //绑定注解框架
            ButterKnife.bind(this, contentView);
            //初始化变量(用户传递进来的参数)
            initVariables(getArguments());
            //初始化视图
            initViews(savedInstanceState);
            //加载数据
            loadData();
        }
        //返回视图
        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 创建视图
     *
     * @param inflater
     * @param container
     * @return
     */
    public abstract View createView(LayoutInflater inflater, ViewGroup container);

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

    /**
     * 获取根视图
     *
     * @return
     */
    public View getContentView() {
        return contentView;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}