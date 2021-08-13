package com.zdy.xiangxue.lesson_UI.widget;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.zdy.xiangxue.R;
import com.zdy.xiangxue.databinding.TitleViewBinding;
import com.zdy.xiangxue.lesson_UI.models.TitleViewViewModel;


/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class TitleView extends BaseCustomView<TitleViewBinding, TitleViewViewModel> {
    public TitleView(Context context) {
        super(context);
    }

    @Override
    public int setViewLayoutId() {
        return R.layout.title_view;
    }

    @Override
    public void setDataToView(TitleViewViewModel data) {
        getDataBinding().setViewModel(data);
    }

    @Override
    public void onRootClick(View view) {
        Toast.makeText(getContext(), "clicked...", Toast.LENGTH_SHORT).show();
    }
}
