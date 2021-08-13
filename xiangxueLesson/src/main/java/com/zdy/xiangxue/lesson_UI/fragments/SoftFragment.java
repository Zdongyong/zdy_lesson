package com.zdy.xiangxue.lesson_UI.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zdy.xiangxue.R;

/**
 * 创建日期：2020/7/8 on 22:14
 * 描述：
 * 作者：zhudongyong
 */
public class SoftFragment extends LazyFragment {

    private static final String TAG = "Fragment";

    public static SoftFragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        SoftFragment fragment = new SoftFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_soft;
    }

    @Override
    protected void intView(View view) {}

    @Override
    protected void onFragmentLoad() {
        Log.i(TAG, "intData: soft");
    }

    @Override
    protected void onFragmentLoadStop() {
        Log.i(TAG, "stopData: soft");
    }
}