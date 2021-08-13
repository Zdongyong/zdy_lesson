package com.zdy.material.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * 创建日期：2020/8/3 on 16:09
 * 描述：
 * 作者：zhudongyong
 */
public class FragmentAdapter extends FragmentStateAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;


    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments,
                           List<String> titles) {
        super(fragmentActivity);
        mFragments = fragments;
        mTitles = titles;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return null==mFragments?0:mFragments.size();
    }
}