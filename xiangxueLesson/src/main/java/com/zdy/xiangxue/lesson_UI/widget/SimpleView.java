package com.zdy.xiangxue.lesson_UI.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;

import com.zdy.xiangxue.MyFragmentPagerAdapter;
import com.zdy.xiangxue.R;
import com.zdy.xiangxue.lesson_UI.fragments.MusicFragment;
import com.zdy.xiangxue.lesson_UI.fragments.VideoFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

/**
 * 创建日期：2020/7/12 on 11:42
 * 描述：自定义Draw-文字绘制
 * 作者：zhudongyong
 *
 * 变大写  shift+comd+X
 *
 */
public class SimpleView extends View {

    private Paint mPaint;

    private String measureText="毛爸爸";
    private String rectText="画布裁剪";

    private int measureHeight,measureWidth;

    private float mPercent = 0.0f;//裁剪百分比


    public SimpleView(Context context) {
        super(context);
        this.mPercent = mPercent;
        initView();
    }

    public SimpleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public SimpleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    public float getPercent() {
        return mPercent;
    }

    public void setPercent(float mPercent) {
        this.mPercent = mPercent;
        invalidate();//重绘
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setTextSize(100);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setTypeface(Typeface.DEFAULT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureHeight = getMeasuredHeight();
        measureWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //y只的是baseline的基准线
        canvas.drawLine(getWidth()/2,0,getWidth()/2,getHeight(),mPaint);
        canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,mPaint);


        //-----------------------------绘制baseline--------------------------------------------------------

        /**
         * Draw the text, with origin at (x,y), using the specified paint. The origin is interpreted
         * based on the Align setting in the paint.
         *
         * @param text The text to be drawn
         * @param x The x-coordinate of the origin of the text being drawn
         * @param y The y-coordinate of the baseline of the text being drawn
         *
         * @param paint The paint used for the text (e.g. color, size, style)
         */


        canvas.drawText(measureText,0,measureHeight/2,mPaint);


        //-----------------------------绘制baseline---文字中间位置------------------------------------------------

        int x = (int) (measureWidth/2 - mPaint.measureText(measureText)/2);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();//字体测量工具
        //fontMetrics.ascent baseline之上至字符正常最高处的距离  负值
        //fontMetrics.descent baseline之下至字符正常最低处的距离 正值
        //fontMetrics.top  baseline之上至字符最高处的距离  负值
        //fontMetrics.bottom baseline之下至字符最低处的距离 正值

        int baseline = (int) ((measureHeight/2  + (fontMetrics.descent - fontMetrics.ascent)/2 - fontMetrics.descent));

        //y 简化 getHeight()/2 - (fontMetrics.ascent+fontMetrics.descent)/2
//        int y = (int) (measureHeight/2 - (fontMetrics.ascent+fontMetrics.descent)/2);
        canvas.drawText(measureText,0,measureText.length(),x,baseline,mPaint);


        //-----------------------------绘制baseline---画布裁剪---字体渐变---------------------------------------------
        canvas.save();
        float width = mPaint.measureText(rectText);//字体宽度
        float left = measureWidth/2 - width / 2;//字体渐变left坐标位置
        Paint.FontMetrics fontMetrics2 = mPaint.getFontMetrics();
        int baseline2 = (int) (measureHeight/4 - (fontMetrics2.ascent+fontMetrics2.descent)/2);
        float right = left + width * mPercent;//字体渐变right坐标位置
        Rect rect = new Rect((int) left,0, (int) right,getWidth());
        canvas.clipRect(rect);
        canvas.drawText(rectText,0,rectText.length(),left,baseline2,mPaint);
        canvas.restore();



        //-----------------------------过渡绘制---------------------------------------------
//        过渡绘制
//        同一个像素点 绘制了多次 2
//
//        真彩色：没有过度绘制
//        蓝色：过度绘制 1 次
//        绿色：过度绘制 2 次
//        粉色：过度绘制 3 次
//        红色：过度绘制 4 次或更多次
    }





}