package com.example.song.application;

import android.content.Context;
import android.util.Log;

import com.example.commonlibrary.base.application.BaseApplication;
import com.example.song.BuildConfig;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Song on 2017/1/20.
 */

public class App extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Log.i("Sophix LoadPath "," SUCCESS ! Info:"+info);
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            Log.i("Sophix LoadPath "," CODE_LOAD_RELAUNCH ! Info:"+info);
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.i("Sophix LoadPath "," ERRROR !   Info:"+info);

                        }
                    }
                }).initialize();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_APP_ID, true);
        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
