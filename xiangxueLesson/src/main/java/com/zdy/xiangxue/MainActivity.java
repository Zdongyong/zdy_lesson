package com.zdy.xiangxue;

import android.content.Intent;
import android.net.Network;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.zdy.log.LogConfig;
import com.zdy.log.MoreLog;
import com.zdy.xiangxue.lesson03_proxy.HookActivity.HookAMSUtil;
import com.zdy.xiangxue.lesson_UI.fragments.MusicFragment;
import com.zdy.xiangxue.lesson_UI.fragments.NewsFragment;
import com.zdy.xiangxue.lesson_UI.fragments.VideoFragment;
import com.zdy.xiangxue.lesson_UI.fragments.NestFragment;
import com.zdy.xiangxue.lesson_UI.widget.MyTabLayout;
import com.zdy.xiangxue.services.MyService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private MyTabLayout mTabLayout;
    private List<String> mTitles = new ArrayList<String>();
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HookAMSUtil.hookActivityManager();
        mViewPager = (ViewPager) findViewById(R.id.view_paper);
        mTabLayout = (MyTabLayout) findViewById(R.id.my_tabLayout);
        fragmentList.add(NewsFragment.newInstance(0));
//        fragmentList.add(Nest01Fragment.newInstance(1));
        fragmentList.add(NestFragment.newInstance(1));
        fragmentList.add(MusicFragment.newInstance(2));
        fragmentList.add(VideoFragment.newInstance(3));
        mTitles.add("新闻");
        mTitles.add("软件");
        mTitles.add("音乐");
        mTitles.add("视频");
        MyFragmentPagerAdapter fragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList,mTitles);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        Intent intentService = new Intent(MainActivity.this, MyService.class);
        this.startService(intentService);

        LogConfig.LogBuilder logBuilder = new LogConfig.LogBuilder()
                .setContext(this);
        logBuilder.builder();

        new SyncConfigThread().start();
    }

    private static class SyncConfigThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    MoreLog.d("more","more","=====more======").flush();
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
