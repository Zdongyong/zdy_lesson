package com.zdy.xiangxue.lesson_UI;

import android.app.admin.SecurityLog;
import android.os.Bundle;
import android.util.StatsLog;

import com.google.android.material.tabs.TabLayout;
import com.zdy.xiangxue.MyFragmentPagerAdapter;
import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson_UI.fragments.MusicFragment;
import com.zdy.xiangxue.lesson_UI.fragments.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

/**
 * 创建日期：2020/7/12 on 11:42
 * 描述：嵌套滑动
 * 作者：zhudongyong
 */
public class NestedActivity03 extends AppCompatActivity {

    private List<Fragment> fragmentList = new ArrayList<>();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<String> mTitles = new ArrayList<String>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested03);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_view);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        fragmentList.add(new MusicFragment());
        fragmentList.add(new VideoFragment());
        mTitles.add("音乐");
        mTitles.add("视频");
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList,mTitles);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

}