package com.example.song.demo.views.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.commonlibrary.mobile.Mobile;
import com.facebook.rebound.ui.Util;

/**
 * <p> Created by 宋华 on 2017/9/13.
 */
public class MyView extends View {
    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        post(new Runnable() {
            @Override
            public void run() {
                mWidth = getWidth();
                mHeight = getHeight();
            }
        });
    }

    private int mWidth;
    private int mHeight;
    Paint mPaint = new Paint();


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        //设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢
        mPaint.setAntiAlias(true);
        //设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        mPaint.setDither(true);
        //设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE
        mPaint.setStyle(Paint.Style.STROKE);
        //当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式Cap.ROUND,或方形样式Cap.SQUARE
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(30);
        canvas.drawCircle(mWidth / 2f - 10, mHeight / 2f - 10, mWidth / 2f, mPaint);

        //模拟实现粗体文字，设置在小字体上效果会非常差
        mPaint.setFakeBoldText(false);
        mPaint.setColor(Color.BLUE);
        //设置该项为true，将有助于文本在LCD屏幕上的显示效果
//        mPaint.setSubpixelText(true);
        mPaint.setStrokeWidth(2);

        mPaint.setTextSize(13 * Mobile.DENSITY);
        canvas.drawText("任涛", mWidth / 2f, mHeight / 2f, mPaint);
    }
}
