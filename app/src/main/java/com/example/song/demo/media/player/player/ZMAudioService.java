package com.example.song.demo.media.player.player;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.song.demo.media.player.ZMAudioPlayer;


/**
 * Created by zz on 2017/4/26.
 * 后台播放音频的service
 */

public class ZMAudioService  extends Service{

    ZMAudioPlayer mAudioPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void prepare(){
        if(mAudioPlayer == null ){
            mAudioPlayer = new ZMAudioPlayer(this);
        }
    }
}
