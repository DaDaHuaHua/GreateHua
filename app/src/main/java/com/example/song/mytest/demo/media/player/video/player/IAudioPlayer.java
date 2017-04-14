package com.example.song.mytest.demo.media.player.video.player;


import android.content.Context;

/**
 * Created by zz on 2017/4/13.
 * 音频
 */

public interface IAudioPlayer extends IMediaPlayer {
    void prepareAsync();
    void setWakeMode( int mode);
    void reset();
}