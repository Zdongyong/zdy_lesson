package com.zdy.xiangxue.lesson_UI.utils;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * 创建日期：8/13/21 on 11:54 PM
 * 描述：
 * 作者：zhudongyong
 */
public class MyFragmentPager extends FragmentStatePagerAdapter {

    List<Fragment> mFragments;
    List<String> mTitles;

    public MyFragmentPager(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * 设置标签栏的标题
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}