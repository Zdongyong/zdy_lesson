package com.zdy.xiangxue.lesson_UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zdy.skinlibrary.SkinManager;
import com.zdy.xiangxue.R;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 创建日期：2020/7/3 on 17:08
 * 描述：自定义View
 * 作者：zhudongyong
 */

public class SkinViewActivity extends AppCompatActivity {

    private Button skin,reset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_view);
        skin = (Button) findViewById(R.id.btn_skin);
        skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadSkin("/data/data/com.zdy.xiangxue/skin/app-debug.apk");
            }
        });
        reset = (Button) findViewById(R.id.btn_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SkinManager.getInstance().loadSkin("");
            }
        });

    }

}
