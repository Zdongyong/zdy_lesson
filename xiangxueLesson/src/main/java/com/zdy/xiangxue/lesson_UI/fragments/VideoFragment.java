package com.zdy.xiangxue.lesson_UI.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson_UI.widget.SimpleView;

/**
 * 创建日期：2020/7/8 on 22:14
 * 描述：
 * 作者：zhudongyong
 */
public class VideoFragment extends LazyFragment {

    private static final String TAG = "Fragment";

    private SimpleView mSimpleView;

    public static VideoFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_video;
    }

    @Override
    protected void intView(View view) {
        mSimpleView = view.findViewById(R.id.simple_view);
    }

    @Override
    protected void onFragmentLoad() {
        Log.i(TAG, "intData: video");
    }

    @Override
    protected void onFragmentLoadStop() {
        Log.i(TAG, "stopData: video");
    }

    @Override
    public void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onStartToLeft();
            }
        },2000);

    }


    private void onStartToLeft(){
        ObjectAnimator.ofFloat(mSimpleView,"percent",0,1).setDuration(5000).start();
    }

    public void onStartTop(View view){
//        mView1.setDirection(ColorChangeTextView2.DIRECTION_TOP);
//        ObjectAnimator.ofFloat(mSimpleView,"progress",0,1).setDuration(2500).start();
    }

    public void onStartBottom(View view){
//        mView1.setDirection(ColorChangeTextView2.DIRECTION_BOTTOM);
//        ObjectAnimator.ofFloat(mSimpleView,"progress",0,1).setDuration(2500).start();
    }

}