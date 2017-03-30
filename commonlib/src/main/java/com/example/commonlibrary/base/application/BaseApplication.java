package com.example.commonlibrary.base.application;

import android.app.Application;

import com.example.commonlibrary.mobile.Mobile;

/**
 * Created by Song on 2016/11/29.
 *
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Mobile.init(this);
    }
}
