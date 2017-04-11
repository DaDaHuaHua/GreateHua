package com.example.song.greathua.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.song.greathua.R;
import com.example.song.greathua.base.CommonActivity;
import com.example.song.greathua.mytest.demo.DemoMain;
import com.example.song.greathua.mytest.homework.HomeworkMain;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by PVer on 2017/4/2.
 *
 */

public class TestMainActivity extends CommonActivity {

    @BindView(R.id.tv_to_homework)
    TextView mTvToHomewok;
    @BindView(R.id.tv_to_demo)
    TextView mTvToDemo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

    }

    @OnClick({R.id.tv_to_homework,R.id.tv_to_demo})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_to_homework:
                startActivity(new Intent(TestMainActivity.this, HomeworkMain.class));
                break;
            case R.id.tv_to_demo:
                startActivity(new Intent(TestMainActivity.this, DemoMain.class));
                break;
        }
    }
}