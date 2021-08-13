package com.zdy.xiangxue.lesson_UI.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zdy.xiangxue.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期：2020/7/12 on 22:24
 * 描述：
 * 作者：zhudongyong
 */
public class RecyclerFragmentAdapter extends RecyclerView.Adapter<RecyclerFragmentAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    public List<String> mData;
    public Context mContext;

    public RecyclerFragmentAdapter(Context context,List<String> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public RecyclerFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.items,parent,false);
        Log.i(TAG, "onCreateViewHolder: viewType"+viewType+" parent:"+parent.getChildCount());
        return new RecyclerFragmentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerFragmentAdapter.MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: position"+position);
        holder.tv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textview);
        }
    }
}