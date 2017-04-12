package com.example.song.mytest.demo.media.player.video.player;

import android.view.View;

import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.video.player.playerImpl.IMediaPlayer;

/**
 * Created by Song on 2017/3/31.
 * 播放器操作接口
 */

public interface IVideoPlayer extends IMediaPlayer{

    /**
     * 设置播放旋转方向
     */
    void setDisplayOrientation(int degree);

    /**
     * 设置缩放比例
     */
    void setDisplayAspectRatio(int ratio);

    /**
     * 获取当前的缩放比例
     */
    int getDisplayAspectRatio();

    /**
     * 设置解码方式 0软解码 1硬解码 2自动解码
     */
    void setDecodeType(int type);
}
