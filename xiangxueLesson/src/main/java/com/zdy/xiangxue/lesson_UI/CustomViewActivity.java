package com.zdy.xiangxue.lesson_UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson03_proxy.HookActivity.HookActivityUtil;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 创建日期：2020/7/3 on 17:08
 * 描述：自定义View
 * 作者：zhudongyong
 */

public class CustomViewActivity extends AppCompatActivity {

    private TextView tv_flexbox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        tv_flexbox = (TextView) findViewById(R.id.tv_flexbox);
        tv_flexbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomViewActivity.this, FlexBoxViewActivity.class);
                startActivity(intent);
            }
        });

    }

}
