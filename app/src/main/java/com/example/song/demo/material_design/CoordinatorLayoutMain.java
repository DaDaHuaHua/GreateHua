package com.example.song.demo.material_design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.song.R;
import com.example.song.base.CommonActivity;

import butterknife.OnClick;

/**
 * Created by PVer on 2017/4/25.
 * 测试coordinatorLayout
 */

public class CoordinatorLayoutMain extends CommonActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_main);
    }

    @OnClick({R.id.fab})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.fab:
                Snackbar.make(findViewById(R.id.fab),"show SnackBar ",Snackbar.LENGTH_SHORT).show();
                break;
        }
    }
}
