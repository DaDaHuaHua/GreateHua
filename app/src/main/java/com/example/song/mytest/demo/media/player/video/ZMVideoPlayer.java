package com.example.song.mytest.demo.media.player.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.video.player.IVideoPlayer;
import com.example.song.mytest.demo.media.player.video.player.playerImpl.PiliVideoPlayer;

/**
 * Created by zz on 2017/3/31.
 * 视屏播放器
 */

public class ZMVideoPlayer extends RelativeLayout {

    /**
     * 更换播放器实现对应的 IVideoPlayerOption即可
     */
    private IVideoPlayer mVideoPlayerOption;

    private PlayerCallback.OnPrepareListener mOnPrepareListener;
    private PlayerCallback.OnErrorListener mOnErrorListener;
    private PlayerCallback.OnCompleteListener mOnCompleteListener;
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
        mVideoPlayerOption = new PiliVideoPlayer(context);
    }

    /**
     * 将配置设置到具体的player中，需要最后调用
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer build() {
        if(mOnPrepareListener == null || mOnErrorListener == null || mOnCompleteListener ==null ) throw  new NullPointerException("u need set listeners before invoke \"build()\"");
        mVideoPlayerOption.setOnPrepareListener(mOnPrepareListener);
        mVideoPlayerOption.setOnErrorListener(mOnErrorListener);
        mVideoPlayerOption.setOnCompleteListener(mOnCompleteListener);
        mVideoPlayerOption.setBufferingView(mBufferingView);
        mVideoPlayerOption.setCoverView(mCoverView);
        mVideoPlayerOption.init();
        View contentView = mVideoPlayerOption.getPlayerLayout();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(contentView,params);
        return this;
    }

    public void start() {
        mVideoPlayerOption.start();
    }

    public void stop() {
        mVideoPlayerOption.stop();
    }

    public void pause() {
        mVideoPlayerOption.pause();
    }

    public void seekTo(long time) {
        mVideoPlayerOption.seekTo(time);
    }


    /**
     * 设置当前播放的是否为在线直播，如果是，底层会有一些播放优化
     * @param videoType  0 回放 1直播
     * @return  ZMVideoPlayer
     */
    public ZMVideoPlayer setVideoType(int videoType){
        mVideoPlayerOption.setVideoType(videoType);
        return this;
    }

    /**
     * 设置解码方式
     * @param type   0软解码 1硬解码 2自动解码
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setDecodeType(int type){
        mVideoPlayerOption.setDecodeType(type);
        return this;
    }

    /**
     * 设置播放的旋转角度
     * @param degree 角度 0 90 270
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setDisplayOrientation(int degree){
        mVideoPlayerOption.setDisplayOrientation(degree);
        return this;
    }

    /**
     * 设置播放地址
     * @param path path
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setVideoPath(String path){
        mVideoPlayerOption.setVideoPath(path);
        return this;
    }

    /**
     * 设置播放比例
     * 主：不合适的比例会导致画面拉伸
     *
     * @param ratio
     * PLVideoTextureView.ASPECT_RATIO_ORIGIN           原始尺寸
     * PLVideoTextureView.ASPECT_RATIO_FIT_PARENT       适应屏幕
     * PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT     全屏铺满
     * PLVideoTextureView.ASPECT_RATIO_16_9             16:9
     * PLVideoTextureView.ASPECT_RATIO_4_3              4:3
     *
     * @return ZMVideoPlayer
     */
    public ZMVideoPlayer setDisplayAspectRatio(int ratio){
        mVideoPlayerOption.setDisplayAspectRatio(ratio);
        return this;
    }

    public int getDisplayAspectRatio(){
        return  mVideoPlayerOption.getDisplayAspectRatio();
    }

    /**
     * 设置缓冲动画。在播放器进入缓冲状态时，自动显示加载界面，缓冲结束后，自动隐藏加载界面。
     * @param view view
     */
    public ZMVideoPlayer setBufferingView(View view){
        this.mBufferingView = view;
        return this;
    }

    /**
     * 设置首屏封面
     * @param view view
     */
    public ZMVideoPlayer setCoverView(View view){
        this.mCoverView = view;
        return this;
    }

    public ZMVideoPlayer setOnPrepareListener(PlayerCallback.OnPrepareListener listener) {
        this.mOnPrepareListener = listener;
        return this;
    }

    public ZMVideoPlayer setOnErrorListener(PlayerCallback.OnErrorListener listener) {
        this.mOnErrorListener = listener;
        return this;
    }

    public ZMVideoPlayer setOnCompleteListener(PlayerCallback.OnCompleteListener listener) {
        this.mOnCompleteListener = listener;
        return this;
    }

}
