package com.example.song.demo.views.vector;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.song.R;
import com.example.song.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by PVer on 2017/4/4.
 */

public class VectorActivity extends BaseActivity {
    @BindView(R.id.iv_vector)
    ImageView mIvVector;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vector_activity);
    }

    @OnClick({R.id.iv_vector})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_vector:
                ((Animatable)mIvVector.getDrawable()).start();
        }
    }
}
