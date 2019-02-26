package com.example.song.demo.views.audioActionView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.song.R;
import com.example.song.base.BaseActivity;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by PVer on 2017/4/8.
 *
 */

public class AudioActionActivity extends BaseActivity {
    @BindView(R.id.audio_action)
    AudioActionView mAudioActionView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_action);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.audio_action:
                break;
        }
    }

}
