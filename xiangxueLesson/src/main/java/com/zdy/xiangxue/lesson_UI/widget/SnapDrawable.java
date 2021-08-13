package com.zdy.xiangxue.lesson_UI.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 创建日期：2020/7/22 on 01:06
 * 描述：
 * 作者：zhudongyong
 */
public class SnapDrawable extends Drawable {

    private Paint mPaint;
    private float BIG_RADIUS = 200f;//大圆半径
    private PointF middlePoint;//中心点
    private int alpha;//透明度

    public SnapDrawable(){
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //防抖
        mPaint.setDither(true);
        //填充
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);//颜色
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        //绘制外面的圈
        mPaint.setStrokeWidth(10);
        middlePoint = new PointF(1.5f * BIG_RADIUS,1.5f * BIG_RADIUS);
        RectF rectF = new RectF(middlePoint.x- 1.5f * BIG_RADIUS,middlePoint.y- 1.5f * BIG_RADIUS,middlePoint.x+1.5f * BIG_RADIUS,middlePoint.y+1.5f * BIG_RADIUS);
        mPaint.setAlpha(100);
        canvas.drawCircle(middlePoint.x,middlePoint.y,BIG_RADIUS,mPaint);
//        canvas.drawArc(rectF,0,-90,false,mPaint);
//        canvas.drawRect(rectF,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    //
    @Override
    public int getIntrinsicWidth() {
        return (int) (3f * BIG_RADIUS);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (3f * BIG_RADIUS);
    }
}