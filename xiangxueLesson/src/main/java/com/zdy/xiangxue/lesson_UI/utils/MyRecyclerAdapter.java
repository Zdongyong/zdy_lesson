package com.zdy.xiangxue.lesson_UI.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zdy.xiangxue.R;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期：8/13/21 on 11:53 PM
 * 描述：
 * 作者：zhudongyong
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    Context mContext;

    public MyRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mllAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "总会有一个人让你想拼了命的努力变好！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 16;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mllAll;

        public ViewHolder(View itemView) {
            super(itemView);
            mllAll = (LinearLayout) itemView.findViewById(R.id.llAll);
        }
    }
}