package com.example.song.mytest.demo.media.player.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.video.option.IVideoPlayerOption;
import com.example.song.mytest.demo.media.player.video.option.optionImpl.PiliPlayerOption;

/**
 * Created by zz on 2017/3/31.
 * 视屏播放器
 */

public class ZMVideoPlayer extends RelativeLayout {

    private IVideoPlayerOption mVideoPlayerOption;
    private PlayerCallback.OnPrepareListener mOnPrepareListener;
    private PlayerCallback.OnErrorListener mOnErrorListener;
    private PlayerCallback.OnCompleteListener mOnCompleteListener;
    private View mBufferingView;

    public ZMVideoPlayer(Context context) {
        this(context, null);
    }

    public ZMVideoPlayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZMVideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mVideoPlayerOption = new PiliPlayerOption(getContext().getApplicationContext());
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

    public ZMVideoPlayer build() {
        if(mOnPrepareListener == null || mOnErrorListener == null || mOnCompleteListener ==null ) throw  new NullPointerException("u need set listeners before invoke \"build()\"");
        mVideoPlayerOption.setOnPrepareListener(mOnPrepareListener);
        mVideoPlayerOption.setOnErrorListener(mOnErrorListener);
        mVideoPlayerOption.setOnCompleteListener(mOnCompleteListener);
        mVideoPlayerOption.setBufferingView(mBufferingView);
        mVideoPlayerOption.init(getContext().getApplicationContext());
        View contentView = mVideoPlayerOption.getPlayerLayout();
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        addView(contentView);
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

    public ZMVideoPlayer setVideoType(int videoType){
        mVideoPlayerOption.setVideoType(videoType);
        return this;
    }

    public ZMVideoPlayer setDecodeType(int type){
        mVideoPlayerOption.setDecodeType(type);
        return this;
    }

    public ZMVideoPlayer setDisplayOrientation(int degree){
        mVideoPlayerOption.setDisplayOrientation(degree);
        return this;
    }

    public ZMVideoPlayer setVideoPath(String path){
        mVideoPlayerOption.setVideoPath(path);
        return this;
    }

    public ZMVideoPlayer setDisplayAspectRatio(int ratio){
        mVideoPlayerOption.setDisplayAspectRatio(ratio);
        return this;
    }

    public int getDisplayAspectRatio(){
        return  mVideoPlayerOption.getDisplayAspectRatio();
    }

    public void setBufferingView(View view){
        this.mBufferingView = view;
    }

}
