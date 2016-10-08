package com.jtechlib.view.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * 状态栏设置工具
 */
public class StatusBarCompat {
    private static final int INVALID_VAL = -1;
    private static final int COLOR_DEFAULT = Color.parseColor("#20000000");

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VAL) {
                activity.getWindow().setStatusBarColor(statusColor);
            }
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int color = COLOR_DEFAULT;
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            if (statusColor != INVALID_VAL) {
                color = statusColor;
            }
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(color);
            contentView.addView(statusBarView, lp);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置状态栏
     *
     * @param statusBar
     */
    public static void setStatusBar(Context context, View statusBar) {
        if (null != statusBar) {
            //判断SDK版本是否大于等于19，大于就让他显示
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                statusBar.setVisibility(View.VISIBLE);
                //设置状态栏高度
                ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
                if (null != layoutParams) {
                    layoutParams.height = getStatusBarHeight(context);
                    statusBar.setLayoutParams(layoutParams);
                } else {
                    statusBar.setLayoutParams(new ViewGroup.LayoutParams(0, getStatusBarHeight(context)));
                }
            } else {
                statusBar.setVisibility(View.GONE);
            }
        }
    }
}