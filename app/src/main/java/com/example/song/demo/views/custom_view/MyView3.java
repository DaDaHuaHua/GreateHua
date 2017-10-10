package com.example.song.demo.views.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p> Created by 宋华 on 2017/9/25.
 */
public class MyView3 extends View {
    public MyView3(Context context) {
        this(context,null);
    }

    public MyView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs ,0);
    }

    public MyView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Paint mPaint = new Paint();
    Shader mShader = new LinearGradient(100 , 100 , 600 ,600 ,
            Color.parseColor("#e91e63") ,  Color.parseColor("#2196f3") ,Shader.TileMode.CLAMP);
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       mPaint.setShader(mShader);
        canvas.drawCircle(300 , 300 , 150 ,mPaint);
    }
}
