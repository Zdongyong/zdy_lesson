package com.zdy.xiangxue.lesson_UI.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdy.xiangxue.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期：2020/7/22 on 23:10
 * 描述：
 * 作者：zhudongyong
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItermHolder> {


    private Context mContext;
    private List<GroupInfo> mDataList = new ArrayList<>();

    public RecyclerViewAdapter(Context context){
        this.mContext = context;
    }

    public Boolean isGroupHead(int position){
        if (0==position){
            return true;
        }else {
            String currentName = getGroupName(position);
            String lastName = getGroupName(position-1);
            if (currentName.equals(lastName)){
                return false;
            }else {
                return true;
            }
        }
    }

    public String getGroupName(int position){
        return mDataList.get(position).getName();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ItermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyler_view,null);
        return new ItermHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ItermHolder holder, int position) {
        holder.textView.setText(mDataList.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return mDataList==null?0:mDataList.size();
    }

    /**
     * RecylerView将activityde datas和adpter的数据源关联才有效 先clear再addAll
     * @param dataList
     */
    public void setDataList(List<GroupInfo> dataList){
        mDataList.clear();
        mDataList.addAll(dataList);
    }

    public class ItermHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ItermHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_tv);
        }
    }
}