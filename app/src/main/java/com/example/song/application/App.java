package com.example.song.application;

import android.content.Context;
import android.util.Log;

import com.example.commonlibrary.base.application.BaseApplication;
import com.example.song.BuildConfig;
import com.example.song.libdb.testdb.MyObjectBox;
import com.facebook.stetho.Stetho;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.bugly.crashreport.CrashReport;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by Song on 2017/1/20.
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
        initHotfix();
        initDB();
        initStetho();
    }


    private BoxStore mBoxStore;

    private  void initDB(){
        mBoxStore = MyObjectBox.builder().androidContext(App.this).build();
        if(BuildConfig.DEBUG){
            new AndroidObjectBrowser(mBoxStore).start(this);
        }
        Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
    }

    public BoxStore getBoxStore(){
        return mBoxStore;
    }

    private void initStetho(){
        Stetho.initializeWithDefaults(this);
    }

    private void initHotfix() {
        SophixManager.getInstance().setContext(this)
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setAesKey(null)
                .setEnableDebug(true)
                //在清单文件配置了就不需要在代码中配置了
                //.setSecretMetaData("24691797-1", "4eaef7cf838232fa638dca7358e02928", "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCj9Ih4CbtZILhG2dD3yqR9/hT41qf45D0e2d/tkdX1QMzd1vIaAoBgjQipQuHbH24sLwq814Bi6c7qFq0qjnW9m8z+yks5dKT0zk05xfSiWclxy1m44Msm7+6JMbIii9UCbX6d3TNsDrAvAFL87anRA20aF+IvrXKfuQSLh3FVMaSKIV7zT4Gq+/eXE12/3DjbJJJibdCG9LhXHqQuB2EJfebr99BOp4z3sBMSeOECJlH3LgLpQY9OQsFOwckFYhdZz5fisXG3SW3yZmxuexu1RJhJ7fIhVbRJ6ehEqR98TTgc1spc8hXEw2rBr9KTH0UhmkAI7eph5GYOFId+Df3JAgMBAAECggEBAIviN4kK7JBHjVHNxy+S8jMY0/XW1utQeCFZTGeaFI0yI1hmaprsZXF7Ate+24ojz9DctfGWf9BkoYgxnr7/Ws9SHV2JZRL5RUAkv9i531Wg+3EeSUema02mScM95jj5StzOZMf1kUjxYzpGsYdgkpFktfIcTHvXJUPHDGyrO3OuEHM47zgNPk3+xsMmENXJ0katr2Tf8MDvXImqnKCDZHFmxz92euHno2Ijp+BXH53HVYrXrVFHoZ5/LDNAzqD/4zr5hpw8u98/ChyQnwjUR1VHnHmQXoLGRCoymdcFia+/0vWLaVfaTqykLcM+oxydqY9Uxre9M7ql0NQo+0XL04ECgYEA0tIgpHxpqD+CuoBQ8nJSlPdL43JgQrI0Os+x0nNbOVa3ayOzgWSZ/m17ogUg74WbUjS8BT/PFRofwisSalJSAYqTfCRlyjG2BF5Q1cciGBuxcVC/edWnIF+VzsXBIzbWisTUagzaP4CrlRohSwp2anzDWlB4mkxc7ZaVB+L1GfECgYEAxxdN+jIOJFgIjoQExg5gPNVBW8TXNwe488GewkR2l8OlSGE2nzVF+GJCIYHlMhQlowrBUwMg8QVpJd7aijxz+Hn6NFuX4Rl8PVbi6YnhPQYg31peEipD8EEolPnvRDJDnHmw0nSHia8GwDUb9V4yN+V2MnvdIUaD7LrwPAVWiVkCgYBBFFq9TCa1qH1ro0bZwQ7bK4gCu4EZtbdH3GuxYqjrh2hxq4CzVeHv185NkrW7Gwx6kQrlt+/uPLVHFFLH/YeRMD0dGoNNqpDcwlzD0Ygl1w6sw1eIfbrs1HUDcqqPe1M5XU+ROUx6Q/czvPBAIQezEbagf6zrPN2G/nAdEiPFkQKBgQC3s4ow3LTt1gj3L1uUQDmOiBuJiKQoU8jVqgdKK4/d0xmUIpca6jYIzOlGL6wCvFJKEad2aEGiAyMVXX765HMBh9NVMlp+ylw34Q74/E9HVntIfAKm8jbD3euLIt8iZtq57BM67WkOouAwxBBrJCJkLycTx/SvOniKG6eFSkuh6QKBgGLRt0bMzVANQIwYCAaZ3pZxRiwZHL5HUNFtdzc5xDn0ZuYy+pxBGun0M61ATMjJGvIT5LWDv/DKEpweYkE18QHRC4gXRxIrP6EHrB1hGRg5fBKbOeP5zqIlJ6/Oo4YycU+dCrG5GSS7l6OKtNvxMAnL9qMACa9rFjMUdfgnhY5d")
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Log.i("Sophix LoadPath ", " SUCCESS ! Info:" + info);
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                            Log.i("Sophix LoadPath ", " CODE_LOAD_RELAUNCH ! Info:" + info);
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.i("Sophix LoadPath ", " ERRROR !   Info:" + info);

                        }
                    }
                }).initialize();

        SophixManager.getInstance().queryAndLoadNewPatch();
    }
}
