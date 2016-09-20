package com.jtechlib.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * fragmentviewpager适配器基类
 * Created by wuxubaiyang on 16/5/5.
 */
public class BaseStateFragmentPagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private List<T> fragments = new ArrayList<>();

    public BaseStateFragmentPagerAdapter(FragmentManager fm, List<T> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return null != fragments ? fragments.size() : 0;
    }
}