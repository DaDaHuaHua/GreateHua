package com.example.commonlibrary.base.application;

import android.app.Application;

import com.example.commonlibrary.mobile.Mobile;

/**
 * Created by Song on 2016/11/29.
 *
 */

public class BaseApplication extends Application {
    public static BaseApplication app;
    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (this) {
            app = this;
        }
        Mobile.init(this);
    }
}
