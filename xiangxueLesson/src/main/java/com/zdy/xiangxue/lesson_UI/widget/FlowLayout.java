package com.zdy.xiangxue.lesson_UI.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

/**
 * 创建日期：2020/7/2 on 23:03
 * 描述：自定义流式布局
 * 作者：zhudongyong
 */

public class FlowLayout extends FrameLayout {

    public static final String TAG = FlowLayout.class.getSimpleName();

    private int mHorizontalSpacing = dp2px(16); //间距
    private int mVerticalSpacing = dp2px(8); //行距

    private List<List<View>> allLines = new ArrayList<>();//所有的行
    private List<Integer> lineHeights = new ArrayList<>();//每一行的行高

    public FlowLayout(@NonNull Context context) {
        super(context);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     *
     * LayoutParams 可以理解为子布局相对于父容器的布局信息对象，存放位置，宽高等信息
     *              是ViewGroup里面的一个内部类
     * MeasureSpec  Android测量
     *              是通过 int 值的位运算来实现的，目的是减少对象的内存分配。
     *              将32位 int 值的前两位作为 mode（将 int 值左位运算30位）
     *                  UNSPECIFIED, 不对View大小做限制，系统使用
     *                  EXACTLY, 确切的大小，如：100dp
     *                  AT_MOST  大小不可超过某数值，如：matchParent, 最大不能超过你爸爸
     *              将后30位作为 size
     *
     * 系统内部是通过MeasureSpec来给View 进行测量工作的,但是我们实际却是只用LayoutParams来设置的
     *
     *
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        clearMeasureParams();
        //TODO 先测量子View
        int childCount = getChildCount();//获取子View个数
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);//父view的最大宽度 后面30位
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);//父view的最大高度

        List<View> lineViews = new ArrayList<>();//当前行的所有View

        int lineHeight = 0;//当前行高
        int lineWidthUsed = 0;//当前行使用的宽度

        int lineNeedWidth = 0;//每一行需要的宽度
        int lineNeedHeight = 0;//每一行需要的高度

        for (int i = 0;i<childCount;i++){//变量所有子View
            View childView = getChildAt(i);
            if (childView.getVisibility()!=View.GONE){
                //获取子View的布局参数
                LayoutParams childLP = (LayoutParams) childView.getLayoutParams();

                //将LayoutParams转化成MeasureSpec

                // getChildMeasureSpec(int spec, int padding, int childDimension)
                // spec- 父View的宽高 HeightMeasureSpec或者WidthMeasureSpec
                // padding-父View左右Padding区域
                // childDimension-子View的宽高
                int measureWidth = getChildMeasureSpec(widthMeasureSpec,paddingLeft+paddingRight,childLP.width);
                int measureHeight = getChildMeasureSpec(widthMeasureSpec,paddingTop+paddingBottom,childLP.height);

                // TODO 度量子View
                childView.measure(measureWidth,measureHeight);

                //获取子View的宽高
                int childMeasureWidth = childView.getMeasuredWidth();
                int childMeasuredHeight = childView.getMeasuredHeight();

                //换行
                if (lineWidthUsed + childMeasureWidth + mHorizontalSpacing>parentWidth){
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);

                    //当前行需要的高度 = 上次使用的高度 + 子view的高度 + 行距
                    lineNeedHeight = lineNeedHeight + childMeasuredHeight + mVerticalSpacing;
                    lineNeedWidth = Math.max(lineNeedWidth,lineWidthUsed+mHorizontalSpacing);

                    lineViews = new ArrayList<>();
                    lineWidthUsed = 0;
                    lineHeight = 0;
                }

                lineViews.add(childView);

                //总的使用的宽度=上一次使用的宽度 + 当前View的宽度 + 横向空格的宽度
                lineWidthUsed = lineWidthUsed + childMeasureWidth + mHorizontalSpacing;
                //当前行的高度=上一次使用的宽度与当前子View的高度的最大值
                lineHeight = Math.max(lineHeight,childMeasuredHeight+mVerticalSpacing);

                //最后一行
                if (i==childCount-1){
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);
                    lineNeedHeight = lineNeedHeight + childMeasuredHeight + mVerticalSpacing;
                    lineNeedWidth = Math.max(lineNeedWidth,lineWidthUsed+mHorizontalSpacing);
                }

            }

        }

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //如果获取到的MeasureSpec类型是确切值，直接用,否则用测量后的值
        int parentMeasureWidth = (widthMode == MeasureSpec.EXACTLY) ? parentWidth:lineNeedWidth;
        int parentMeasureHeight = (heightMode == MeasureSpec.EXACTLY) ? parentHeight:lineNeedHeight;

        //TODO 测量父View
        setMeasuredDimension(parentMeasureWidth,parentMeasureHeight);

    }

    /**
     * 布局
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     *
     * 视图坐标系
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int lineCount = allLines.size();
        Log.i(TAG, "onLayout: "+lineCount);
        int currentLeft = getPaddingLeft();
        int currentTop = getPaddingTop();

         for (int i=0;i<lineCount;i++){//布局竖向
             List<View> viewList = allLines.get(i);
             for (int j=0;j<viewList.size();j++){//布局横向
                 View childView = viewList.get(j);
                 int viewLeft = currentLeft;
                 int viewtTop = currentTop;
                 int measureWidth = childView.getMeasuredWidth();
                 int measuredHeight = childView.getMeasuredHeight();
                 int viewRight = currentLeft+measureWidth;
                 int viewtBottom = currentTop+measuredHeight;
                 childView.layout(viewLeft,viewtTop,viewRight,viewtBottom);

                 currentLeft = viewRight + mHorizontalSpacing;
             }
             currentLeft = getPaddingLeft();
             currentTop = currentTop+lineHeights.get(i) + mVerticalSpacing;
         }

    }


//    /**
//     * 绘制
//     * @param canvas
//     */
//    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void clearMeasureParams() {
        allLines.clear();
        lineHeights.clear();
    }


    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
