package com.example.song.demo.media;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.example.song.R;
import com.example.song.base.CommonActivity;
import com.example.song.demo.media.player.PiliAudioPlayerActivity;
import com.example.song.demo.media.player.PiliVideoPlayerActivity;

import butterknife.OnClick;

/**
 * Created by zz on 2017/4/13.
 *
 */

public class PlayerMain extends CommonActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_main);
    }

    @OnClick ({R.id.tv_video_player,R.id.tv_audio_player})
    @Override
    public void onClick(View v){
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_video_player:
                startActivity(new Intent(PlayerMain.this, PiliVideoPlayerActivity.class));
                // startActivity(new Intent(DemoMain.this, TestActivity.class));
                break;
            case R.id.tv_audio_player:
                startActivity(new Intent(PlayerMain.this, PiliAudioPlayerActivity.class));
                break;
        }
    }
}
