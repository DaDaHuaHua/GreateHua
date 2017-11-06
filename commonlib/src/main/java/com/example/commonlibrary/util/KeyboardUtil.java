package com.example.commonlibrary.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

/**
 * <p> Created by 宋华 on 2017/10/26.
 */
public class KeyboardUtil {
    /**
     * 隐藏底部虚拟按键
     */
    public static void hideBottomNavigation(Activity context) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            context.getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            context.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * 显示出底部虚拟按键
     */
    public static void showBottomNavigation(Activity context) {
        context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * 将底部导航栏变成半透明
     */
    public static void setBottomNavigationTranslucent(Activity context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
