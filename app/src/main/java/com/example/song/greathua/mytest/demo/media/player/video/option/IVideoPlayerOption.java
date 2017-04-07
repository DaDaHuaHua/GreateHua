package com.example.song.greathua.mytest.demo.media.player.video.option;

import android.content.Context;
import android.view.View;

import com.example.song.greathua.mytest.demo.media.player.video.callback.PlayerCallback;
import com.pili.pldroid.player.IMediaController;

/**
 * Created by Song on 2017/3/31.
 * 播放器操作接口
 */

public interface IVideoPlayerOption {

    /**
     * 初始化播放器操作
     * @param context
     */
    void init(Context context , int decodeType);

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
     *设置播放路径
     */
    void setVideoPath(String path);

    /**
     * 设置播放的是直播还是回放
     * @param type 0:回放 1：直播
     */
    void setVideoType(int type);

    /**
     * 设置解码方式 0软解码 1硬解码 2自动解码
     */
    void setDecodeType(int type);

    //=================== 播放状态回调 =======================
    void setOnPrepareListener(PlayerCallback.OnPrepareListener listener);
    void setOnErrorListener(PlayerCallback.OnErrorListener listener);
    void setOnCompleteListener(PlayerCallback.OnCompleteListener listener);
}
