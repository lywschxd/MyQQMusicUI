package com.dd.myqqmusicui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dong on 2015-10-12 0012.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> lists;

    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.lists = list;
    }

    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getCount() {
        return lists.size();
    }
}
