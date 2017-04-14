package com.example.song.mytest.demo.media.player.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.video.player.IVideoPlayer;
import com.example.song.mytest.demo.media.player.video.player.playerimpl.PiliVideoPlayer;

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

    private PlayerCallback.OnPrepareListener mOnPrepareListener;
    private PlayerCallback.OnErrorListener mOnErrorListener;
    private PlayerCallback.OnCompleteListener mOnCompleteListener;
    private PlayerCallback.OnInfoListener mOnInfoListener;
    private PlayerCallback.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private PlayerCallback.OnSeekCompleteListener mOnSeekCompleteListener;
    private View mBufferingView;
    private View mCoverView;

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
    public ZMVideoPlayer build() {
        mVideoPlayer.setOnPrepareListener(mOnPrepareListener);
        mVideoPlayer.setOnErrorListener(mOnErrorListener);
        mVideoPlayer.setOnCompleteListener(mOnCompleteListener);
        mVideoPlayer.setBufferingView(mBufferingView);
        mVideoPlayer.setCoverView(mCoverView);
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

    public void release() {
        mVideoPlayer.release();
    }

    /**
     * 设置当前播放的是否为在线直播，如果是，底层会有一些播放优化
     *
     * @param videoType 0 回放 1直播
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setVideoType(int videoType) {
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
        this.mBufferingView = view;
        return this;
    }

    /**
     * 设置首屏封面
     *
     * @param view view
     */
    public ZMVideoPlayer setCoverView(View view) {
        this.mCoverView = view;
        return this;
    }

    public ZMVideoPlayer setOnPrepareListener(PlayerCallback.OnPrepareListener listener) {
        this.mOnPrepareListener = listener;
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnPrepareListener(listener);
        }
        return this;
    }

    public ZMVideoPlayer setOnErrorListener(PlayerCallback.OnErrorListener listener) {
        this.mOnErrorListener = listener;
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnErrorListener(listener);
        }
        return this;
    }

    public ZMVideoPlayer setOnCompleteListener(PlayerCallback.OnCompleteListener listener) {
        this.mOnCompleteListener = listener;
        if(mVideoPlayer !=null ){
            mVideoPlayer.setOnCompleteListener(listener);
        }
        return this;
    }


    public ZMVideoPlayer setOnInfoListener(PlayerCallback.OnInfoListener listener) {
        this.mOnInfoListener = listener;
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnInfoListener(listener);
        }
        return this;
    }

    public ZMVideoPlayer setOnBufferingUpdateListener(PlayerCallback.OnBufferingUpdateListener listener) {
        this.mOnBufferingUpdateListener = listener;
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnBufferingUpdateListener(listener);
        }
        return this;
    }

    public ZMVideoPlayer setOnSeekCompleteListener(PlayerCallback.OnSeekCompleteListener listener) {
        this.mOnSeekCompleteListener = listener;
        if (mVideoPlayer != null) {
            mVideoPlayer.setOnSeekCompleteListener(listener);
        }
        return this;
    }


}
