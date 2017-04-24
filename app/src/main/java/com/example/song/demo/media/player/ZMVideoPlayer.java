package com.example.song.demo.media.player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.song.demo.media.player.callback.PlayerCallback;
import com.example.song.demo.media.player.player.IMediaController;
import com.example.song.demo.media.player.player.IMediaPlayer;
import com.example.song.demo.media.player.player.IVideoPlayer;
import com.example.song.demo.media.player.player.playerimpl.PiliVideoPlayer;

import java.io.IOException;

/**
 * Created by zz on 2017/3/31.
 * 视屏播放器
 */

public class ZMVideoPlayer extends RelativeLayout {

    /**
     * 更换播放器实现对应的 IVideoPlayerOption即可
     */
    private IVideoPlayer mVideoPlayer;


    public ZMVideoPlayer(Context context) {
        this(context, null);
    }

    public ZMVideoPlayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZMVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mVideoPlayer = new PiliVideoPlayer(context);
    }

    /**
     * 将配置设置到具体的player中，需要最后调用
     *
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer build() throws IOException {
        mVideoPlayer.init();
        View contentView = mVideoPlayer.getPlayerLayout();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(contentView, params);
        return this;
    }

    public void start() {
        mVideoPlayer.start();
    }

    public void stop() {
        mVideoPlayer.stop();
    }

    public void pause() {
        mVideoPlayer.pause();
    }

    public void seekTo(long time) {
        mVideoPlayer.seekTo(time);
    }

    public void reset() {
        mVideoPlayer.reset();
    }


    public void release() {
        mVideoPlayer.release();
    }

    public boolean isPlaying() {
        return mVideoPlayer.isPlaying();
    }

    /**
     * 设置当前播放的是否为在线直播，如果是，底层会有一些播放优化
     *
     * @param videoType 0 回放 1直播
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setPlayerType(int videoType) {
        mVideoPlayer.setPlayerType(videoType);
        return this;
    }

    /**
     * 设置解码方式
     *
     * @param type 0软解码 1硬解码 2自动解码
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setDecodeType(int type) {
        mVideoPlayer.setDecodeType(type);
        return this;
    }

    /**
     * 设置播放的旋转角度
     *
     * @param degree 角度 0 90 270
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setDisplayOrientation(int degree) {
        mVideoPlayer.setDisplayOrientation(degree);
        return this;
    }

    /**
     * 设置播放地址
     *
     * @param path path
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setVideoPath(String path) throws IOException {
        mVideoPlayer.setPath(path);
        return this;
    }

    /**
     * 设置播放比例
     * 主：不合适的比例会导致画面拉伸
     *
     * @param ratio PLVideoTextureView.ASPECT_RATIO_ORIGIN           原始尺寸
     *              PLVideoTextureView.ASPECT_RATIO_FIT_PARENT       适应屏幕
     *              PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT     全屏铺满
     *              PLVideoTextureView.ASPECT_RATIO_16_9             16:9
     *              PLVideoTextureView.ASPECT_RATIO_4_3              4:3
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setDisplayAspectRatio(int ratio) {
        mVideoPlayer.setDisplayAspectRatio(ratio);
        return this;
    }

    public int getDisplayAspectRatio() {
        return mVideoPlayer.getDisplayAspectRatio();
    }

    /**
     * 设置缓冲动画。在播放器进入缓冲状态时，自动显示加载界面，缓冲结束后，自动隐藏加载界面。
     *
     * @param view view
     */
    public ZMVideoPlayer setBufferingView(View view) {
        mVideoPlayer.setBufferingView(view);
        return this;
    }

    /**
     * 设置首屏封面
     *
     * @param view view
     */
    public ZMVideoPlayer setCoverView(View view) {
        mVideoPlayer.setCoverView(view);
        return this;
    }


    public IMediaPlayer getIMediaPlayer() {
        return mVideoPlayer;
    }

    public void setMediaController(IMediaController mediaController) {
        if (mVideoPlayer != null) {
            mVideoPlayer.setMediaController(mediaController);
        }
    }

    public IMediaController getMediaController() {
        if (mVideoPlayer != null) {
            return mVideoPlayer.getMediaController();
        } else {
            throw new NullPointerException("mVideoPlayer is null");
        }
    }

    /***
     * 监听播放器的 prepare 过程
     * prepare 过程主要包括：创建资源、建立连接、请求码流等等
     * 当 prepare 完成后， 会回调onPrepare 。可以调用播放器的 start() 启动播放
     */
    public ZMVideoPlayer setOnPrepareListener(PlayerCallback.OnPrepareListener listener) {
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnPrepareListener(listener);
        }
        return this;
    }

    /***
     * 监听播放器的错误消息
     * 返回值决定了该错误是否已经被处理，如果返回 false，则代表没有被处理，下一步则会触发 onCompletion 消息。
     */
    public ZMVideoPlayer setOnErrorListener(PlayerCallback.OnErrorListener listener) {
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnErrorListener(listener);
        }
        return this;
    }

    /***
     * 监听播放结束
     * 具体参见详细播放器的实现
     */
    public ZMVideoPlayer setOnCompleteListener(PlayerCallback.OnCompleteListener listener) {
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnCompleteListener(listener);
        }
        return this;
    }

    /**
     * 在播放器启动后，播放器发生状态变化时调用该对象的 onInfo 方法，同步状态信息。
     * 播放信息见具体播放器的实现
     */
    public ZMVideoPlayer setOnInfoListener(PlayerCallback.OnInfoListener listener) {
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnInfoListener(listener);
        }
        return this;
    }

    /**
     * 已经缓冲的数据量占整个视频时长的百分比。
     * 仅在播放文件和回放时才有效
     */
    public ZMVideoPlayer setOnBufferingUpdateListener(PlayerCallback.OnBufferingUpdateListener listener) {
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnBufferingUpdateListener(listener);
        }
        return this;
    }

    /**
     * seek 完成后回调
     * 当调用的播放器的 seekTo 方法后，会在 seek 成功后触发该回调。
     */
    public ZMVideoPlayer setOnSeekCompleteListener(PlayerCallback.OnSeekCompleteListener listener) {
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnSeekCompleteListener(listener);
        }
        return this;
    }


}
