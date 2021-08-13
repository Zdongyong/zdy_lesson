package com.zdy.xiangxue.lesson_UI.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 创建日期：2020/7/22 on 23:48
 * 描述：
 * 作者：zhudongyong
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "ItemDecoration";

    private Context mContext;
    private int groupHeaderHeight;//head的高度

    private Paint headPaint;
    private Paint textPaint;
    private Rect textRect;
    private int lineHeight = 4;//分割线高度

    public ItemDecoration(Context context) {
        this.mContext = context;
        groupHeaderHeight = dp2px(context, 150);
        textPaint = new Paint();
        textPaint.setColor(Color.LTGRAY);
        textPaint.setTextSize(dp2px(mContext,60));
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);

        headPaint = new Paint();
        headPaint.setColor(Color.RED);

        textRect = new Rect();
    }

    private int dp2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale * 0.5f);
    }

    /**
     * 在addView之前执行
     *
     * 绘制头部
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getAdapter() instanceof RecyclerViewAdapter){
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) parent.getAdapter();
            //获取所有子view
            int count = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth()-parent.getPaddingRight();
            for (int i = 0; i < count; i++) {
                //获取子view  注意：包含分割线所有view.getTop是指分割线的top
                View view = parent.getChildAt(i);
                //获取view的位置
                int position = parent.getChildLayoutPosition(view);
                boolean isHead = adapter.isGroupHead(position);
                //处理parent的PaddingTop
                if (view.getTop() - groupHeaderHeight - parent.getPaddingTop() < 0) continue;
                if (isHead){
                    //绘制头部区域
                    c.drawRect(left,view.getTop()-groupHeaderHeight,
                            right,view.getTop(),headPaint);
                    //绘制头部文字
                    String groupName = adapter.getGroupName(position);
                    textPaint.getTextBounds(groupName,0,groupName.length(),textRect);
                    c.drawText(groupName,view.getWidth()/2 - textRect.width()/2,
                            view.getTop() - groupHeaderHeight/2 + textRect.height()/2,textPaint);
                }else{//绘制分割线
                    c.drawRect(left,view.getTop()-lineHeight,right,view.getTop(),headPaint);
                }

            }
        }

    }


    /**
     * 在addView之后执行
     *
     * 绘制吸顶效果
     *
     * onDrawOver会覆盖onDraw
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getAdapter() instanceof RecyclerViewAdapter){
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) parent.getAdapter();
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
            // 返回可见区域内的第一个item的position
            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
            //获取第一个ViewHolder
            View itemView = parent.findViewHolderForLayoutPosition(firstVisibleItemPosition).itemView;
            int left = parent.getPaddingLeft();
            int top = parent.getPaddingTop();
            int right = parent.getWidth() - parent.getPaddingRight();

            //可移动的head是onDraw绘制的，不可移动的是onDrawOver绘制的
            //当可移动的head移动到顶部的时候，onDrawOver的top和bottom都是不断变化的
            //当可移动的head准备进入top位置的的时候，进行head判断，其实当可移动的head已经当做head
            boolean isHead = adapter.isGroupHead(firstVisibleItemPosition + 1);
            String groupName = adapter.getGroupName(firstVisibleItemPosition);
            String nextName = adapter.getGroupName(firstVisibleItemPosition+1);
            textPaint.getTextBounds(groupName,0,groupName.length(),textRect);
            if (isHead){
                //下一个itemView的bottom<当不可移动的head的bottom(groupHeaderHeight)的时候，不可移动的head就要被顶掉
                int bottom = Math.min(groupHeaderHeight,itemView.getBottom()-parent.getPaddingTop());
                Log.i(TAG, "onDrawOver: "+bottom);
                //绘制头部区域
                c.drawRect(left,top,right,top+bottom,headPaint);
                //绘制头部文字
                c.clipRect(left,top,right,top+bottom);//裁剪不能超过此区域
                c.drawText(groupName,itemView.getWidth()/2 - textRect.width()/2,
                        top + groupHeaderHeight / 2 + textRect.height()/2,textPaint);
            }else {
                //绘制头部区域
                c.drawRect(left,top,right,top+groupHeaderHeight,headPaint);
                //绘制头部文字
                c.drawText(groupName,itemView.getWidth()/2 - textRect.width()/2,
                        top + groupHeaderHeight / 2 + textRect.height()/2,textPaint);
            }

        }
    }

    /**
     * 每个item的偏移
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getAdapter() instanceof RecyclerViewAdapter){
            RecyclerViewAdapter adapter = (RecyclerViewAdapter) parent.getAdapter();
            //获取当前view位置
            int childLayoutPosition = parent.getChildLayoutPosition(view);
            if (adapter.isGroupHead(childLayoutPosition)){
                outRect.set(0, groupHeaderHeight, 0, 0);
            }else {
                outRect.set(0, lineHeight, 0, 0);
            }
        }

    }
}