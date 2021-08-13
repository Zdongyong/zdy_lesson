package com.zdy.material.activities;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.zdy.material.R;
import com.zdy.material.adapter.FragmentAdapter;
import com.zdy.material.fragments.NestScrollFragment;
import com.zdy.material.fragments.gallery.GalleryFragment;
import com.zdy.material.fragments.home.HomeFragment;
import com.zdy.material.fragments.slideshow.SlideshowFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 创建日期：2020/8/3 on 17:01
 * 描述：
 * 作者：zhudongyong
 */
public class NestScrollActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> mTitles = new ArrayList<String>();
    private FragmentAdapter fragmentAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest);
        viewPager = findViewById(R.id.view_pager);
        fragmentList.add(NestScrollFragment.newInstance("传统嵌套"));
        mTitles.add("传统嵌套");
        fragmentAdapter = new FragmentAdapter(this,fragmentList,mTitles);
        viewPager.setAdapter(fragmentAdapter);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}