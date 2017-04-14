package com.jtechlib.Util;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * toolbar的链式调用方法
 */
public final class ToolbarChain {
    private Context context;
    private Toolbar toolbar;

    private ToolbarChain(Context context, Toolbar toolbar) {
        this.context = context;
        this.toolbar = toolbar;
    }

    public static ToolbarChain build(Context context, Toolbar toolbar) {
        return new ToolbarChain(context, toolbar);
    }

    public ToolbarChain setTitle(@StringRes int resId) {
        toolbar.setTitle(resId);
        return this;
    }

    public ToolbarChain setTitle(CharSequence title) {
        toolbar.setTitle(title);
        return this;
    }

    public ToolbarChain setSubTitle(@StringRes int resId) {
        toolbar.setSubtitle(resId);
        return this;
    }

    public ToolbarChain setSubTitle(CharSequence title) {
        toolbar.setSubtitle(title);
        return this;
    }

    public ToolbarChain setTitleTextAppearance(Context context, @StyleRes int resId) {
        toolbar.setTitleTextAppearance(context, resId);
        return this;
    }

    public ToolbarChain setTitleTextColor(int resId) {
        toolbar.setTitleTextColor(context.getResources().getColor(resId));
        return this;
    }

    public ToolbarChain setSubTitleTextColor(int resId) {
        toolbar.setSubtitleTextColor(context.getResources().getColor(resId));
        return this;
    }

    public ToolbarChain setLogo(int resId) {
        toolbar.setLogo(resId);
        return this;
    }

    public ToolbarChain setNavigationIcon(int resId) {
        toolbar.setNavigationIcon(resId);
        return this;
    }

    public ToolbarChain setNavigationIcon(int resId, View.OnClickListener onClickListener) {
        toolbar.setNavigationIcon(resId);
        toolbar.setNavigationOnClickListener(onClickListener);
        return this;
    }

    public ToolbarChain setNavigationClick(View.OnClickListener onClickListener) {
        toolbar.setNavigationOnClickListener(onClickListener);
        return this;
    }

    public ToolbarChain inflateMenu(@MenuRes int resId) {
        toolbar.inflateMenu(resId);
        return this;
    }

    public ToolbarChain setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        return this;
    }

    public ToolbarChain setContentInsetStartWithNavigation(int insetStartWithNavigation) {
        toolbar.setContentInsetStartWithNavigation(insetStartWithNavigation);
        return this;
    }

    public ToolbarChain setContentInsetEndWithActions(int insetEndWithActions) {
        toolbar.setContentInsetEndWithActions(insetEndWithActions);
        return this;
    }

    public ToolbarChain setContentInsetsAbsolute(int contentInsetLeft, int contentInsetRight) {
        toolbar.setContentInsetsAbsolute(contentInsetLeft, contentInsetRight);
        return this;
    }

    public ToolbarChain setContentInsetsRelative(int contentInsetStart, int contentInsetEnd) {
        toolbar.setContentInsetsRelative(contentInsetStart, contentInsetEnd);
        return this;
    }
}