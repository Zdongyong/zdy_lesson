package com.zdy.xiangxue.lesson_UI;

import android.os.Bundle;
import android.widget.ImageView;

import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson_UI.utils.GroupInfo;
import com.zdy.xiangxue.lesson_UI.utils.ItemDecoration;
import com.zdy.xiangxue.lesson_UI.utils.RecyclerViewAdapter;
import com.zdy.xiangxue.lesson_UI.widget.SnapDrawable;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期：2020/7/12 on 11:42
 * 描述：吸顶
 * 作者：zhudongyong
 */
public class MountingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    private List<GroupInfo> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mounting);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new ItemDecoration(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewAdapter(this);
        initData();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDataList(datas);
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 4; j++) {
                if (i % 2 == 0) {
                    datas.add(new GroupInfo("明教", "无忌" + i));
                } else {
                    datas.add(new GroupInfo("武当", "三丰" + i));
                }
            }
        }
    }

}