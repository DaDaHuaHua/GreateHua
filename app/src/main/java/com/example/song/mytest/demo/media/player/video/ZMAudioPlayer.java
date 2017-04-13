package com.example.song.mytest.demo.media.player.video;

import android.content.Context;

import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.video.player.IAudioPlayer;
import com.example.song.mytest.demo.media.player.video.player.playerimpl.PiliAudioPlayer;

import java.io.IOException;

/**
 * Created by zz on 2017/4/13.
 */

public class ZMAudioPlayer {
    /**
     * 更换播放器实现对应的 IAudioPlayer即可
     */
    private IAudioPlayer mAudioPlayer;

    private PlayerCallback.OnPrepareListener mOnPrepareListener;
    private PlayerCallback.OnErrorListener mOnErrorListener;
    private PlayerCallback.OnCompleteListener mOnCompleteListener;

    private int volumeL ;
    private int volumeR ;


    public ZMAudioPlayer(Context context) {
        mAudioPlayer = new PiliAudioPlayer(context);
    }


    public ZMAudioPlayer build() {
        mAudioPlayer.setOnPrepareListener(mOnPrepareListener);
        mAudioPlayer.setOnCompleteListener(mOnCompleteListener);
        mAudioPlayer.setOnErrorListener(mOnErrorListener);
        mAudioPlayer.init();
        return this;
    }

    public ZMAudioPlayer prepareAsync() {
        mAudioPlayer.prepareAsync();
        return this;
    }

    public void start() {
        mAudioPlayer.start();
    }

    public void pause() {
        mAudioPlayer.pause();
    }

    public void stop() {
        mAudioPlayer.stop();
    }

    public ZMAudioPlayer seekTo(long time) {
        mAudioPlayer.seekTo(time);
        return this;
    }

    public void release(){
        if(mAudioPlayer!= null){
            mAudioPlayer.release();
        }
    }

    public void reset(){
        if(mAudioPlayer!=null){
            mAudioPlayer.reset();
        }
    }

    public ZMAudioPlayer setWakeMode(int mode) {
        mAudioPlayer.setWakeMode(mode);
        return this;
    }

    public ZMAudioPlayer setVolume(float l, float r) {
        mAudioPlayer.setVolume(l, r);
        return this;
    }

    public ZMAudioPlayer setPlayerType(int type) {
        mAudioPlayer.setPlayerType(type);
        return this;
    }

    public ZMAudioPlayer setDecodeType(int type) {
        mAudioPlayer.setDecodeType(type);
        return this;
    }

    public ZMAudioPlayer setPath(String path) throws IOException {
        mAudioPlayer.setPath(path);
        return this;
    }

    public ZMAudioPlayer setOnPrepareListener(PlayerCallback.OnPrepareListener listener) {
        this.mOnPrepareListener = listener;
        return this;
    }

    public ZMAudioPlayer setOnErrorListener(PlayerCallback.OnErrorListener listener) {
        this.mOnErrorListener = listener;
        return this;
    }

    public ZMAudioPlayer setOnCompleteListener(PlayerCallback.OnCompleteListener listener) {
        this.mOnCompleteListener = listener;
        return this;
    }


}
