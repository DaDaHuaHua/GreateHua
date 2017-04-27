package com.example.song.demo.media;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.commonlibrary.rxbus.RxBus;
import com.example.commonlibrary.util.StringUtil;
import com.example.song.R;
import com.example.song.base.CommonActivity;
import com.example.song.demo.media.player.PiliAudioPlayerActivity;
import com.example.song.demo.media.player.PiliVideoPlayerActivity;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by zz on 2017/4/13.
 */

public class PlayerMain extends CommonActivity {

    @BindView(R.id.tv_video_player)
    TextView mTvVideoPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_main);
        RxBus.getDefault().toObservable(String.class)
                //.compose(this.<String>bindToLifecycle())
                .compose(this.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (StringUtil.equals(s, "ACTION_PLAY")) {
                            mTvVideoPlayer.append("接收到 ACTION_PLAY");
                        }
                    }
                });
    }

    @OnClick({R.id.tv_video_player, R.id.tv_audio_player})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
