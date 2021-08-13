package com.zdy.xiangxue.lesson_UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson03_proxy.HookActivity.HookAMSUtil;
import com.zdy.xiangxue.lesson03_proxy.HookActivity.HookActivityUtil;
import com.zdy.xiangxue.lesson03_proxy.ProxyActivity;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

/**
 * 创建日期：2020/7/12 on 11:42
 * 描述：嵌套滑动
 * 作者：zhudongyong
 */
public class NestedActivity extends AppCompatActivity {

    private AppCompatButton btNested,btNoNested,btMounting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested);
        btNoNested = (AppCompatButton) findViewById(R.id.junmp_no_nested);
        btNoNested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NestedActivity.this, NestedActivity01.class));
            }
        });

        btNested = (AppCompatButton) findViewById(R.id.junmp_nested);
        btNested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NestedActivity.this, NestedActivity02.class));
            }
        });

        btMounting = (AppCompatButton) findViewById(R.id.junmp_mounting);
        btMounting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NestedActivity.this, NestedActivity03.class));
            }
        });

    }

}