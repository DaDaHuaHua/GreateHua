package com.example.song.mytest.demo.media.player.player;

import android.view.View;

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
     * 获取当前的缩放比例
     */
    int getDisplayAspectRatio();


}
