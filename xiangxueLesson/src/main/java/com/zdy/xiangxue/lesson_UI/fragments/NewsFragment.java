package com.zdy.xiangxue.lesson_UI.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson03_proxy.HookActivity.HookActivityUtil;
import com.zdy.xiangxue.lesson03_proxy.ProxyActivity;
import com.zdy.xiangxue.lesson_UI.BarActivity;
import com.zdy.xiangxue.lesson_UI.CustomViewActivity;
import com.zdy.xiangxue.lesson_UI.FishActivity;
import com.zdy.xiangxue.lesson_UI.MountingActivity;
import com.zdy.xiangxue.lesson_UI.NestedActivity;
import com.zdy.xiangxue.lesson_UI.SkinViewActivity;
import com.zdy.xiangxue.lesson_rxJava.RxJavaActivity;

import javax.annotation.Nullable;

/**
 * 创建日期：2020/7/8 on 22:14
 * 描述：
 * 作者：zhudongyong
 */
public class NewsFragment extends LazyFragment {

    private static final String TAG = "Fragment";

    private Button btJunp,junpRx,junpView,junpSkin,junpNested,junpFish,junpMounting,junpBar;

    public static NewsFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    protected void intView(View view) {

        btJunp = (Button) view.findViewById(R.id.junp);
        btJunp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProxyActivity.class).putExtra("name","xiaoming");
                startActivity(intent);
            }
        });
        junpRx = (Button) view.findViewById(R.id.junp_rx);
        junpRx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RxJavaActivity.class);
                startActivity(intent);
            }
        });
        junpView = (Button) view.findViewById(R.id.junp_view);
        junpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomViewActivity.class);
                startActivity(intent);
            }
        });
        junpSkin = (Button) view.findViewById(R.id.junp_skin);
        junpSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SkinViewActivity.class);
                startActivity(intent);
            }
        });
        junpNested = (Button) view.findViewById(R.id.junp_nested);
        junpNested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NestedActivity.class);
                startActivity(intent);
            }
        });
        junpFish = (Button) view.findViewById(R.id.junp_fish);
        junpFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FishActivity.class);
                startActivity(intent);
            }
        });
        junpMounting = (Button) view.findViewById(R.id.jump_mounting);
        junpMounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MountingActivity.class);
                startActivity(intent);
            }
        });
        junpBar = (Button) view.findViewById(R.id.jump_toolBar);
        junpBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onFragmentLoad() {
        Log.i(TAG, "intData: news");
    }

    @Override
    protected void onFragmentLoadStop() {
        Log.i(TAG, "stopData: news");
    }
}