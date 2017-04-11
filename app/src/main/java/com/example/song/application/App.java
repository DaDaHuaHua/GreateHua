package com.example.song.application;

import android.content.Context;

import com.example.commonlibrary.base.application.BaseApplication;
import com.example.song.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Song on 2017/1/20.
 *
 */

public class App extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_APP_ID, true);
    }
}
