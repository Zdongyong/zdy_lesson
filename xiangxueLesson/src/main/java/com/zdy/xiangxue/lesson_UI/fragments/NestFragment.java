package com.zdy.xiangxue.lesson_UI.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.zdy.xiangxue.MyFragmentPagerAdapter;
import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson_UI.widget.MyTabLayout;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


/**
 * 嵌套
 */
public class NestFragment extends LazyFragment {

    private static final String TAG = "Fragment";

    private ViewPager mViewPager;
    private MyTabLayout mTabLayout;
    private List<String> mTitles = new ArrayList<String>();
    private List<Fragment> fragmentList = new ArrayList<>();

    public static NestFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        NestFragment fragment = new NestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Override
    protected void intView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.view_paper);
        mTabLayout = (MyTabLayout) view.findViewById(R.id.my_tabLayout);
        fragmentList.add(Nest01Fragment.newInstance(0));
        fragmentList.add(Nest02Fragment.newInstance(1));
        mTitles.add("嵌套1");
        mTitles.add("嵌套2");
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(),fragmentList,mTitles);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onFragmentLoad() {
        Log.i(TAG, "intData: nest");
    }

    @Override
    protected void onFragmentLoadStop() {
        Log.i(TAG, "stopData: nest");
    }
}
