package com.zdy.xiangxue.lesson_UI.widget;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 创建日期：2020/7/18 on 21:54
 * 描述：游动锦鲤
 *
 * 作者：zhudongyong
 */
public class FishDrawable extends Drawable {

    private static final String TAG = FishDrawable.class.getSimpleName();

    private Path mPath;
    private Paint mPaint;//画笔
    private int OTHER_ALPHA = 110;//透明度
    private int BODY_ALPHA = 160;
    private float currentValue = 0;//小鱼动画抖动频率
    private PointF headPoint;

    /**
     * 鱼数值配置
     */
    private float HEAD_RADIUS = 50f;//鱼头半径  作为鱼基础长度
    private PointF middlePoint;//鱼的重心坐标
    private float FISH_START_ANGLE = 90;//鱼头开始角度

    private float BODY_LENGTH = (float) (3.2 * HEAD_RADIUS);//鱼身的长度（头中心到大圆中心的距离）
    private float FINS_LENGTH = (float) (1.3 * HEAD_RADIUS);//鱼鳍的长度
    private float FINS_FIND_LENGTH = (float) (0.9 * HEAD_RADIUS);//鱼鳍相对于鱼头圆心点的距离
    private float FINS_CONTROL_LENGTH = (float) (1.8 * FINS_LENGTH);//鱼鳍控制点长度

    private float BIG_CIRCLE_RADIUS = (float) (0.7 * HEAD_RADIUS);//大圆半径
    private float MIDDLE_CIRCLE_RADIUS = (float) (0.6 * BIG_CIRCLE_RADIUS);//中圆半径
    private float SMALL_CIRCLE_RADIUS = (float) (0.4 * MIDDLE_CIRCLE_RADIUS);//小圆半径

    // --寻找尾部中圆圆心的线长
    private final float FIND_MIDDLE_CIRCLE_LENGTH = BIG_CIRCLE_RADIUS * (0.6f + 1);
    // --小圆圆心到中圆圆心的线长
    private final float FIND_SMALL_CIRCLE_LENGTH = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f);
    // --中圆圆心到大三角形底边中心点的线长
    private final float FIND_TRIANGLE_LENGTH = MIDDLE_CIRCLE_RADIUS * 2.2f;


    public FishDrawable(){
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setStyle(Paint.Style.FILL);//填充
        mPaint.setDither(true);//防抖
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71);//颜色

        middlePoint = new PointF(4.19f * HEAD_RADIUS,4.19f * HEAD_RADIUS);

        //定义一个熟悉动画，设置摆动的范围
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 3600f);
        // 动画周期
        valueAnimator.setDuration(15 * 1000);
        //设置重复次数
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //设置重复模式
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentValue = (float) animation.getAnimatedValue();
                Log.i(TAG, "onAnimationUpdate: "+currentValue);

                invalidateSelf();//刷新
            }
        });
        valueAnimator.start();

    }


    /**
     * 将于向右旋转90，鱼头朝右，鱼身体中心为坐标原点
     *
     *
     * @param canvas
     */

    private float frequence = 1f;

    @Override
    public void draw(@NonNull Canvas canvas) {
//        float fishAngle = FISH_START_ANGLE;
        float fishAngle = (float) (FISH_START_ANGLE + Math.sin(Math.toRadians(currentValue * 1.2 * frequence)) * 10);

        //画鱼头
        headPoint = drawHead(canvas,fishAngle);

        //绘制右边鱼鳍
        PointF rightPoint = calculatePoint(headPoint,FINS_FIND_LENGTH,fishAngle - 110);
        drawFins(canvas,rightPoint,fishAngle,true);

        //绘制左边鱼鳍
        PointF leftPoint = calculatePoint(headPoint,FINS_FIND_LENGTH,fishAngle + 110);
        drawFins(canvas,leftPoint,fishAngle,false);

        //绘制节支
        PointF bigPoint = drawSegment(canvas,headPoint,fishAngle);

        //绘制躯干
        drawBody(canvas,headPoint,bigPoint,fishAngle);
    }

    /**
     * 画鱼头
     *
     * 计算鱼头圆心的x,y坐标
     *
     */
    private PointF drawHead(Canvas canvas,float fishAngle) {
        PointF headPonit = calculatePoint(middlePoint,BODY_LENGTH / 2,fishAngle);
        canvas.drawCircle(headPonit.x,headPonit.y,HEAD_RADIUS,mPaint);
        return headPonit;
    }

    /**
     * 画躯干
     * @param canvas
     */
    private void drawBody(Canvas canvas,PointF headPoint,PointF bigPoint,float fishAngle) {
        //躯干四个点的坐标 鱼身体横向
        PointF bodyUpperRightPoint = calculatePoint(headPoint,HEAD_RADIUS,fishAngle+90);//头部圆圈上面的点
        PointF bodyBottomRightPoint = calculatePoint(headPoint,HEAD_RADIUS,fishAngle-90);//头部圆圈下面的点
        PointF bodyUpperLeftPoint = calculatePoint(bigPoint,BIG_CIRCLE_RADIUS,fishAngle+90);//大圆上面的点
        PointF bodyBottomLeftPoint = calculatePoint(bigPoint,BIG_CIRCLE_RADIUS,fishAngle-90);//大圆下面的点

        // 控制点
        // 二阶贝塞尔曲线的控制点 --- 决定鱼的胖瘦
        PointF controlUpper = calculatePoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle + 130);
        PointF controlRight = calculatePoint(headPoint, BODY_LENGTH * 0.56f,
                fishAngle - 130);

        mPath.reset();
        mPath.moveTo(bodyUpperRightPoint.x,bodyUpperRightPoint.y);// 将画笔移动到起始点
        // 二阶贝塞尔曲线
        mPath.quadTo(controlUpper.x,controlUpper.y,bodyUpperLeftPoint.x,bodyUpperLeftPoint.y);
        mPath.lineTo(bodyBottomLeftPoint.x,bodyBottomLeftPoint.y);
        mPath.quadTo(controlRight.x,controlRight.y,bodyBottomRightPoint.x,bodyBottomRightPoint.y);
        mPaint.setAlpha(BODY_ALPHA);
        canvas.drawPath(mPath,mPaint);
    }


    /**
     * 画鱼鳍
     *
     * 二阶贝塞尔曲线
     *
     *
     */
    private void drawFins(Canvas canvas,PointF startPoint,float fishAngle,boolean isRight) {
        //结束点
        PointF endPoint = calculatePoint(startPoint,FINS_LENGTH,fishAngle - 180);
        // 控制点
        PointF controlPoint = calculatePoint(startPoint,FINS_CONTROL_LENGTH,
                isRight?fishAngle-115:fishAngle+115);

        mPath.reset();
        mPath.moveTo(startPoint.x,startPoint.y);// 将画笔移动到起始点
        // 二阶贝塞尔曲线
        mPath.quadTo(controlPoint.x,controlPoint.y,endPoint.x,endPoint.y);
        canvas.drawPath(mPath,mPaint);
    }


    /**
     * 画节支1
     *
     * 动画第一个节支比第二个要快 所以第一个使用cos
     *
     */
    private PointF drawSegment(Canvas canvas,PointF headPoint,float fishAngle) {

        float firstSegmentAngle = (float) (fishAngle + Math.cos(Math.toRadians(currentValue * 1.5 * frequence)) * 15);
        float secendSegmentAngle = (float) (fishAngle + Math.sin(Math.toRadians(currentValue * 1.5 * frequence)) * 35);
//        segmentAngle = (float) (fishAngle + Math.sin(Math.toRadians(currentValue * 1.5 * frequence)) * 35);

        //绘制大圆
        PointF bigCenterPonit = calculatePoint(headPoint,BODY_LENGTH,fishAngle-180);
        canvas.drawCircle(bigCenterPonit.x,bigCenterPonit.y,BIG_CIRCLE_RADIUS,mPaint);

        //绘制中圆
        PointF middleCenterPonit = calculatePoint(bigCenterPonit,BIG_CIRCLE_RADIUS+MIDDLE_CIRCLE_RADIUS,firstSegmentAngle-180);
        canvas.drawCircle(middleCenterPonit.x,middleCenterPonit.y,MIDDLE_CIRCLE_RADIUS,mPaint);
        //绘制小圆
        PointF smallCenterPonit = calculatePoint(middleCenterPonit,FIND_SMALL_CIRCLE_LENGTH,secendSegmentAngle-180);
        canvas.drawCircle(smallCenterPonit.x,smallCenterPonit.y,SMALL_CIRCLE_RADIUS,mPaint);

        //绘制第一个梯形

        PointF upperRightPonit = calculatePoint(middleCenterPonit,MIDDLE_CIRCLE_RADIUS,firstSegmentAngle+90);//梯形上底右边
        PointF upperLeftPonit = calculatePoint(middleCenterPonit,MIDDLE_CIRCLE_RADIUS,firstSegmentAngle-90);//梯形上底左边
        PointF bottomRightPonit = calculatePoint(bigCenterPonit,BIG_CIRCLE_RADIUS,firstSegmentAngle+90);//梯形下底右边
        PointF bottomLeftPonit = calculatePoint(bigCenterPonit,BIG_CIRCLE_RADIUS,firstSegmentAngle-90);//梯形下底左边

        mPath.reset();
        mPath.moveTo(upperRightPonit.x,upperRightPonit.y);
        mPath.lineTo(upperLeftPonit.x,upperLeftPonit.y);
        mPath.lineTo(bottomLeftPonit.x,bottomLeftPonit.y);
        mPath.lineTo(bottomRightPonit.x,bottomRightPonit.y);
        canvas.drawPath(mPath,mPaint);


        //绘制第二个梯形
        PointF smallUpperRightPonit = calculatePoint(smallCenterPonit,SMALL_CIRCLE_RADIUS,secendSegmentAngle+90);//梯形上底右边
        PointF smallUpperLeftPonit = calculatePoint(smallCenterPonit,SMALL_CIRCLE_RADIUS,secendSegmentAngle-90);//梯形上底左边
        PointF smallBottomRightPonit = calculatePoint(middleCenterPonit,MIDDLE_CIRCLE_RADIUS,secendSegmentAngle+90);//梯形下底右边
        PointF smallBottomLeftPonit = calculatePoint(middleCenterPonit,MIDDLE_CIRCLE_RADIUS,secendSegmentAngle-90);//梯形下底左边

        mPath.reset();
        mPath.moveTo(smallUpperRightPonit.x,smallUpperRightPonit.y);
        mPath.lineTo(smallUpperLeftPonit.x,smallUpperLeftPonit.y);
        mPath.lineTo(smallBottomLeftPonit.x,smallBottomLeftPonit.y);
        mPath.lineTo(smallBottomRightPonit.x,smallBottomRightPonit.y);
        canvas.drawPath(mPath,mPaint);

        //绘制大三角形
        PointF bigCenterPoint = calculatePoint(middleCenterPonit,FIND_TRIANGLE_LENGTH,secendSegmentAngle-180);//三角形底部中心坐标
        PointF bigRightPonit = calculatePoint(bigCenterPoint,BIG_CIRCLE_RADIUS,secendSegmentAngle+90);//三角形右边
        PointF bigLeftPonit = calculatePoint(bigCenterPoint,BIG_CIRCLE_RADIUS,secendSegmentAngle-90);//三角形左边
        mPath.reset();
        mPath.moveTo(middleCenterPonit.x,middleCenterPonit.y);
        mPath.lineTo(bigRightPonit.x,bigRightPonit.y);
        mPath.lineTo(bigLeftPonit.x,bigLeftPonit.y);
        canvas.drawPath(mPath,mPaint);

        //绘制小三角形
        PointF smallCenterPoint = calculatePoint(middleCenterPonit,FIND_TRIANGLE_LENGTH-20,secendSegmentAngle-180);//三角形底部中心坐标
        PointF smallRightPonit = calculatePoint(smallCenterPoint,MIDDLE_CIRCLE_RADIUS,secendSegmentAngle+90);//三角形右边
        PointF smallLeftPonit = calculatePoint(smallCenterPoint,MIDDLE_CIRCLE_RADIUS,secendSegmentAngle-90);//三角形左边
        mPath.reset();
        mPath.moveTo(middleCenterPonit.x,middleCenterPonit.y);
        mPath.lineTo(smallRightPonit.x,smallRightPonit.y);
        mPath.lineTo(smallLeftPonit.x,smallLeftPonit.y);
        canvas.drawPath(mPath,mPaint);
        return bigCenterPonit;
    }


    /**
     * 计算偏移量
     * @param startPointF  起始点坐标
     * @param length 要求的点到起始点的直线距离 -- 线长
     * @param angle 鱼当前的朝向角度
     *
     *              sinA = a/c --> sinA * c = a --> 得到B点的y坐标
     * cosA = b/c --> cosA * c = b --> 得到B点的x坐标
     * Math.sin()、Math.cos()的参数是弧度。坐标是按数学中的坐标。
     * Math.toRadians() 将角度转成弧度。
     * 圆是360度，也是2π弧度，即360°=2π
     */
    PointF calculatePoint(PointF startPointF, float length, float angle){

        float x = (float) (Math.cos(Math.toRadians(angle)) * length);

        float y = (float) (Math.sin(Math.toRadians(angle-180)) * length);

        return new PointF(startPointF.x+x,startPointF.y+y);
    }




    /**
     * 设置Drawable的大小（鱼的旋转360所需要的基础大小）
     *
     * 8.38的计算
     *
     * @return
     */
    @Override
    public int getIntrinsicWidth() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (8.38f * HEAD_RADIUS);
    }

    /**
     * 透明度
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }


    /**
     * 颜色过滤
     * @param colorFilter
     */
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }


    /**
     * 这个方法的意思是获得不透明度。 有几个值：PixelFormat：UNKNOWN，TRANSLUCENT，TRANSPARENT，或者 OPAQUE。
     * ~OPAQUE：便是完全不透明，遮盖在他下面的所有内容
     * ~TRANSPARENT：透明，完全不显示任何东西
     * ~TRANSLUCENT：只有绘制的地方才覆盖底下的内容。
     * 这个值，可以根据setAlpha中设置的值进行调整。比如，alpha == 0时设置为PixelFormat.TRANSPARENT。在alpha == 255时设置为PixelFormat.OPAQUE。在其他时候设置为PixelFormat.TRANSLUCENT。
     * @return
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public PointF getMiddlePoint() {
        return middlePoint;
    }

    public float getFrequence() {
        return frequence;
    }

    public void setFrequence(float frequence) {
        this.frequence = frequence;
    }

    public PointF getHeadPoint() {
        return headPoint;
    }

    public void setHeadPoint(PointF headPoint) {
        this.headPoint = headPoint;
    }

    public float getHEAD_RADIUS() {
        return HEAD_RADIUS;
    }

    public void setHEAD_RADIUS(float HEAD_RADIUS) {
        this.HEAD_RADIUS = HEAD_RADIUS;
    }


    public float getFISH_START_ANGLE() {
        return FISH_START_ANGLE;
    }

    public void setFISH_START_ANGLE(float FISH_START_ANGLE) {
        this.FISH_START_ANGLE = FISH_START_ANGLE;
    }
}