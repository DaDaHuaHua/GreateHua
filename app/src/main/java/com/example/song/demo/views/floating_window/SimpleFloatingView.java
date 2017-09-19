package com.example.song.demo.views.floating_window;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.commonlibrary.mobile.Mobile;
import com.example.commonlibrary.util.ToastUtil;
import com.example.song.R;
import com.example.song.demo.views.floating_window.service.MyWindowManager;

/**
 * Created by 33105 on 2017/9/16.
 */

public class SimpleFloatingView extends LinearLayout {

    private WindowManager.LayoutParams mParams;
    private WindowManager windowManager;

    public SimpleFloatingView(final Context context) {
        super(context);
        inflate(context, R.layout.simple_floating_window, this);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMessage("点击了window!!!");
                MyWindowManager.removeSimpleWindow(context, SimpleFloatingView.this);
            }
        });
    }


    private float xInView;
    private float yInView;
    private float xInScreen;
    private float yInScreen;
    private float xDownInScreen;
    private float yDownInScreen;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - Mobile.STATUS_BAR_HEIGHT;
                break;

            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - Mobile.STATUS_BAR_HEIGHT;
                mParams.x = (int) (xInScreen - xInView);
                mParams.y = (int) (yInScreen - yInView);
                windowManager.updateViewLayout(this,mParams);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setParams(WindowManager.LayoutParams params){
        this.mParams = params;
    }
}
