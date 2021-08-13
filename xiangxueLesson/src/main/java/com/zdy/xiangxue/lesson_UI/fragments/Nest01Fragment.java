package com.zdy.xiangxue.lesson_UI.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zdy.xiangxue.R;

/**
 * 创建日期：2020/7/8 on 22:14
 * 描述：
 * 作者：zhudongyong
 */
public class Nest01Fragment extends LazyFragment {

    private static final String TAG = "Fragment";

    private TextView mtx;

    public static Nest01Fragment newInstance(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        Nest01Fragment fragment = new Nest01Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_soft;
    }

    @Override
    protected void intView(View view) {
        mtx = view.findViewById(R.id.tx_view);
        mtx.setText("嵌套一");
    }

    @Override
    protected void onFragmentLoad() {
        Log.i(TAG, "intData: Nest01");
    }

    @Override
    protected void onFragmentLoadStop() {
        Log.i(TAG, "stopData: Nest01");
    }
}