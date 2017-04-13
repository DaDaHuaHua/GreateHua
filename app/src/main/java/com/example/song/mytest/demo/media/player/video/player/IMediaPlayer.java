package com.example.song.mytest.demo.media.player.video.player;

import android.view.View;

import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;

import java.io.IOException;

/**
 * Created by zz on 2017/4/12.
 *
 */

interface IMediaPlayer {
    /**
     * 初始化播放器操作
     */
    void init();

    void start();

    void stop();

    void pause();

    void seekTo(long time);

    void release();

    /**
     * 设置播放音量
     *
     * @param l 左声道 0.0~1.0
     * @param r 右声道 0.0~1.0
     */
    void setVolume(float l, float r);

    /**
     * 设置播放路径
     */
    void setPath(String path) throws IOException;

    /**
     * 设置播放的是直播还是回放
     *
     * @param type 0:回放 1：直播
     */
    void setPlayerType(int type);

    /**
     * 设置解码方式 0软解码 1硬解码 2自动解码
     */
    void setDecodeType(int type);

    //=================== 播放状态回调 =======================
    void setOnPrepareListener(PlayerCallback.OnPrepareListener listener);

    void setOnErrorListener(PlayerCallback.OnErrorListener listener);

    void setOnCompleteListener(PlayerCallback.OnCompleteListener listener);

}
