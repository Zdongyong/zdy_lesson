package com.zdy.xiangxue.lesson_UI.models;

import android.view.View;

import com.zdy.xiangxue.lesson_UI.interfaces.ICustomView;
import com.zdy.xiangxue.lesson_UI.models.BaseCustomViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    ICustomView view;

    public BaseViewHolder(ICustomView view) {
        super((View) view);
        this.view = view;
    }

    public void bind(@NonNull BaseCustomViewModel item) {
        view.setData(item);
    }
}