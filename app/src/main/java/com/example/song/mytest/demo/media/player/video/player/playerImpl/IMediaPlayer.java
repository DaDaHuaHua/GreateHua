package com.example.song.mytest.demo.media.player.video.player.playerImpl;

import android.view.View;

import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;

/**
 * Created by zz on 2017/4/12.
 *
 */

public interface IMediaPlayer {
    /**
     * 初始化播放器操作
     */
    void init();
    void start();
    void stop();
    void pause();
    void seekTo(long time);

    /**
     * 设置缓冲加载动画
     */
    void setBufferingView(View v);

    /**
     * 设置首屏封面
     */
    void setCoverView(View v);


    /**
     * 需要初始化具体播放View
     */
    View getPlayerLayout();

    /**
     *设置播放路径
     */
    void setVideoPath(String path);

    /**
     * 设置播放的是直播还是回放
     * @param type 0:回放 1：直播
     */
    void setVideoType(int type);



    //=================== 播放状态回调 =======================
    void setOnPrepareListener(PlayerCallback.OnPrepareListener listener);
    void setOnErrorListener(PlayerCallback.OnErrorListener listener);
    void setOnCompleteListener(PlayerCallback.OnCompleteListener listener);

}
