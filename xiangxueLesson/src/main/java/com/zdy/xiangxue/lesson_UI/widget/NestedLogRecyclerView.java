package com.zdy.xiangxue.lesson_UI.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期：2020/7/12 on 18:10
 * 描述：
 * 作者：zhudongyong
 */
public class NestedLogRecyclerView extends RecyclerView {
    public NestedLogRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public NestedLogRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedLogRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){

    }
}