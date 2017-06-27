package com.example.song.demo.dragsort;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.song.R;
import com.example.song.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zz on 2017/5/16.
 *
 */

public class DSMain  extends BaseActivity {

    @BindView(R.id.tv_common01)
    TextView mTvDSLV;
    @BindView(R.id.tv_common02)
    TextView mTvDSGV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_xml);
        mTvDSLV.setText("DSLV");
        mTvDSGV.setText("DSGV");
    }

    @OnClick({R.id.tv_common01 , R.id.tv_common02})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_common01:
                startActivity(new Intent(this, DslvMain.class));
                break;
            case R.id.tv_common02:
                startActivity(new Intent(this , DsgvMain.class));
                break;
        }
    }
}
