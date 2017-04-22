package com.example.song;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.song.base.CommonActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class EntranceActivity extends CommonActivity {

    @BindView(R.id.tv_to_app)
     TextView mTvToApp;
    @BindView(R.id.tv_to_test)
     TextView mTvToTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
    }

    @OnClick({R.id.tv_to_app,R.id.tv_to_test})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case  R.id.tv_to_app:

                break;
            case R.id.tv_to_test:
                startActivity(new Intent(EntranceActivity.this, TestMainActivity.class));
                break;
        }
    }
}
