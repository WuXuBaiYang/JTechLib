package com.jtechlib.view.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import butterknife.ButterKnife;

/**
 * popupwindow基类
 * Created by jianghan on 2016/9/27.
 */

public abstract class BasePopupWindow extends PopupWindow {

    private Context context;

    public BasePopupWindow(Context context) {
        super(context);
        //获取context
        this.context = context;
        //初始化参数
        initVariables();
        //初始化视图
        initViews();
    }

    public void initVariables() {
        //设置参数
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setBackgroundDrawable(new ColorDrawable());
        this.setOutsideTouchable(true);
        this.setAnimationStyle(0);
        this.setFocusable(true);
    }

    /**
     * 设置视图
     *
     * @param resId
     */
    public void setContentView(@LayoutRes int resId) {
        setContentView(LayoutInflater.from(getContext()).inflate(resId, null, false));
    }

    @Override
    public void setContentView(View contentView) {
        ButterKnife.bind(this, contentView);
        super.setContentView(contentView);
    }

    /**
     * 设置背景颜色
     *
     * @param resId
     */
    public void setBackgroundColor(@ColorRes int resId) {
        setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(resId)));
    }

    public Context getContext() {
        return context;
    }

    protected abstract void initViews();
}