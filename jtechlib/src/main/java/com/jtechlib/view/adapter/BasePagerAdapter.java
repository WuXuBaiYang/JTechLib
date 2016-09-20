package com.jtechlib.view.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图viewpager适配器基类
 * Created by wuxubaiyang on 16/5/6.
 */
public class BasePagerAdapter<T extends View> extends PagerAdapter {
    private List<T> views = new ArrayList<>();

    public BasePagerAdapter(List<T> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return null != views ? views.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }
}