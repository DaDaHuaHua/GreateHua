package com.example.song.demo.views.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.commonlibrary.mobile.Mobile;
import com.example.song.R;

import java.io.InputStream;

/**
 * <p> Created by 宋华 on 2017/9/13.
 */
public class MyView2 extends View {
    public MyView2(Context context) {
        this(context, null);
    }

    public MyView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MyView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
         Bitmap bitmap =readBitmap(getContext(),R.mipmap.ic_launcher);
           canvas.drawBitmap(bitmap,0,0,mPaint);
        canvas.drawColor(Color.parseColor("#88880000"));
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(100, 100, 500, 300, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawOval(100,600,500,800,mPaint);

        mPaint.setColor(Color.parseColor("#88ff0000"));
        canvas.drawRoundRect(100 ,900,500,1200,50f,50f,mPaint);

    }

    public Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opts);
    }
}
