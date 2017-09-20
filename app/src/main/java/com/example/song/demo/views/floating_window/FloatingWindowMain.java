package com.example.song.demo.views.floating_window;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Toast;

import com.example.commonlibrary.util.ToastUtil;
import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.example.song.demo.views.floating_window.service.FloatingWindowService;

/**
 * <p> Created by 宋华 on 2017/9/14.
 */
public class FloatingWindowMain extends BaseActivity {
    private static final int OVERLAY_PERMISSION_REQ_CODE = 0x11;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_window);
        findViewById(R.id.start_floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFloating();
            }
        });
        if (Build.VERSION.SDK_INT >= 23) {
            askForPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void askForPermission() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
        } else {
            ToastUtil.showMessage("已经获取到悬浮窗权限！");
        }
    }

    private void startFloating() {
        startService(new Intent(this, FloatingWindowService.class));
        finish();
    }
}
