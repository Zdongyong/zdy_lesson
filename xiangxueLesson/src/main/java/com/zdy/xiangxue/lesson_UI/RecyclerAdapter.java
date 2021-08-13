package com.zdy.xiangxue.lesson_UI;

import android.view.ViewGroup;

import com.zdy.xiangxue.lesson_UI.models.BaseViewHolder;
import com.zdy.xiangxue.lesson_UI.models.TitleViewViewModel;
import com.zdy.xiangxue.lesson_UI.widget.TitleView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期：2020/7/12 on 17:54
 * 描述：
 * 作者：zhudongyong
 */
public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public List<String> mData;

    public RecyclerAdapter(List<String> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(new TitleView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bind(new TitleViewViewModel(mData.get(position)));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}