package com.zdy.material.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zdy.material.R;

import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;

public class MyNestedScrollParent extends LinearLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper mParentHelper;
    private int mImgHeight;

    public MyNestedScrollParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final ImageView imageView = (ImageView) findViewById(R.id.imageview);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mImgHeight <= 0) {
                    mImgHeight = imageView.getMeasuredHeight();
                }
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean isImageVisiable = (dy > 0 && getScrollY() < mImgHeight) || (dy < 0 && target.getScrollY() <= 0);
        if (isImageVisiable) {
            consumed[1] = dy;
            scrollBy(0, dy);
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y > mImgHeight) {
            y = mImgHeight;
        }
        if (y < 0) {
            y = 0;
        }
        super.scrollTo(x, y);
    }

}
