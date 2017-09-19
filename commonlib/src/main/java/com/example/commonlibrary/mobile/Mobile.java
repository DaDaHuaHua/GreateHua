package com.example.commonlibrary.mobile;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by Song on 2017/1/20.
 */


public class Mobile {

    /**
     * 屏幕宽度
     */
    public static int SCREEN_WIDTH;

    /**
     * 屏幕高度
     */
    public static int SCREEN_HEIGHT;

    /**
     * 屏幕density
     */
    public static float DENSITY;

    /**
     * 屏幕density
     */
    public static float SCALED_DENSITY;

    /***
     * 屏幕状态栏高度
     * @param context
     */
    public static int STATUS_BAR_HEIGHT;

    public static void init(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGHT = metrics.heightPixels;
        DENSITY = metrics.density;
        SCALED_DENSITY = metrics.scaledDensity;
        metrics = null;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("Mobile","SCREEN_WIDTH="+SCREEN_WIDTH+"  SCREEN_HEIGHT="+SCREEN_HEIGHT+"   DENSITY="+DENSITY);
    }


}
