package com.example.song.mytest.demo.media.player.player;

import com.example.song.mytest.demo.media.player.callback.PlayerCallback;
import com.pili.pldroid.player.*;

import java.io.IOException;

/**
 * Created by zz on 2017/4/12.
 */

public interface IMediaPlayer {
    /**
     * 初始化播放器操作
     */
    void init() throws IOException;

    void start();

    void stop();

    void pause();

    void seekTo(long time);

    void release();

    void reset();

    boolean isPlaying();

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
     * 获取当前播放类型
     *
     * @return 0 回放  1直播
     */
    int getPlayerType();

    /**
     * 获取当前播放类型
     * @return 0 音频 1 视频
     */
    int getMediaType();

    /**
     * 获取播放总时长
     *
     * @return long
     */
    long getDuration();

    /**
     * 获取当前播放时长
     *
     * @return long
     */
    long getCurrentPosition();

    /**
     * 获取当前缓冲占比（仅在回放有效）
     * @return
     */
    int getBufferPercentage();

    /**
     * 设置解码方式 0软解码 1硬解码 2自动解码
     */
    void setDecodeType(int type);

    /**
     * 设置MediaController
     */
    void setMediaController(IMediaController mediaController);

    IMediaController getMediaController();

    //=================== 播放状态回调 =======================

    /**
     * 当 prepare 完成后回调，下一步则可以调用播放器的 start() 启动播放。
     * prepare 过程 ：创建资源、建立连接、请求码流等等，
     *
     * @param listener listener
     */
    void setOnPrepareListener(PlayerCallback.OnPrepareListener listener);

    /**
     * 给MediaController调用
     * 在palyer的OnPrepared之后回调给mediaController
     *
     * @param listener
     */
    void setControllerOnPrepareListener(PlayerCallback.OnPrepareListener listener);

    /**
     * 播放错误回调
     * 播放错误信息见具体播放器的实现
     *
     * @param listener listener
     */
    void setOnErrorListener(PlayerCallback.OnErrorListener listener);

    /**
     * 播放结束回调
     *
     * @param listener listener
     */
    void setOnCompleteListener(PlayerCallback.OnCompleteListener listener);

    /**
     * 在播放器启动后，播放器发生状态变化时调用该对象的 onInfo 方法，同步状态信息。
     * 播放信息见具体播放器的实现
     *
     * @param listener listener
     */
    void setOnInfoListener(PlayerCallback.OnInfoListener listener);


    /**
     * 已经缓冲的数据量占整个视频时长的百分比。
     * 仅在播放文件和回放时才有效
     *
     * @param listener listener
     */
    void setOnBufferingUpdateListener(PlayerCallback.OnBufferingUpdateListener listener);

    /**
     * seek 完成后回调
     * 当调用的播放器的 seekTo 方法后，会在 seek 成功后触发该回调。
     *
     * @param listener
     */
    void setOnSeekCompleteListener(PlayerCallback.OnSeekCompleteListener listener);

    interface OnControllerSetListener {
        void onMediaControllerSet(IMediaPlayer player);
    }

}
