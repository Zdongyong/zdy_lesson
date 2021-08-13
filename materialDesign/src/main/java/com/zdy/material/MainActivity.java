package com.zdy.material;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.zdy.material.adapter.FragmentAdapter;
import com.zdy.material.fragments.gallery.GalleryFragment;
import com.zdy.material.fragments.home.HomeFragment;
import com.zdy.material.fragments.home.HomeViewModel;
import com.zdy.material.fragments.slideshow.SlideshowFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NestedScrollView bottomSheet;
    private BottomSheetBehavior behavior;
    private ViewPager2 viewPager2;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String> mTitles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back_icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏标题

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_left);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.open();
            }
        });

        viewPager2 = findViewById(R.id.view_pager2);

        fragmentList.add(GalleryFragment.newInstance(1));
        fragmentList.add(HomeFragment.newInstance(2));
        fragmentList.add(SlideshowFragment.newInstance(3));
        mTitles.add("新闻");
        mTitles.add("软件");
        mTitles.add("音乐");
        fragmentAdapter = new FragmentAdapter(this,fragmentList,mTitles);
        viewPager2.setAdapter(fragmentAdapter);

//        bottomSheet = findViewById(R.id.bottom_sheet);
//        behavior = BottomSheetBehavior.from(bottomSheet);
//        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_layout, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
