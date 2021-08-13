package com.zdy.xiangxue.lesson_UI.interfaces;

import com.zdy.xiangxue.lesson_UI.models.BaseCustomViewModel;

public interface ICustomView<S extends BaseCustomViewModel> {
    void setData(S data);
}
