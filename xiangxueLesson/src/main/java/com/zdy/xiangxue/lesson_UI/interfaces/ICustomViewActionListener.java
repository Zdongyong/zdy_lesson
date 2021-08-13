package com.zdy.xiangxue.lesson_UI;

import android.view.View;

import com.zdy.xiangxue.lesson_UI.models.BaseCustomViewModel;

public interface ICustomViewActionListener {
    String ACTION_ROOT_VIEW_CLICKED = "action_root_view_clicked";

    void onAction(String action, View view, BaseCustomViewModel viewModel);
}
