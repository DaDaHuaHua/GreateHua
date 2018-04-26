package com.example.song.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.example.song.R;
import com.example.song.base.BaseActivity;

/**
 * Created by 33105 on 2018/4/26.
 */

public class TestActivity  extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
