package com.example.song.mytest.demo.media.player.video;

import android.content.Context;
import android.util.Log;

import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.video.player.IAudioPlayer;
import com.example.song.mytest.demo.media.player.video.player.playerimpl.PiliAudioPlayer;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;

/**
 * Created by zz on 2017/4/13.
 *
 */

public class ZMAudioPlayer {
    /**
     * 更换播放器实现对应的 IAudioPlayer即可
     */
    private IAudioPlayer mAudioPlayer;

    private PlayerCallback.OnPrepareListener mOnPrepareListener;
    private PlayerCallback.OnErrorListener mOnErrorListener;
    private PlayerCallback.OnCompleteListener mOnCompleteListener;
    private PlayerCallback.OnInfoListener mOnInfoListener;
    private PlayerCallback.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private PlayerCallback.OnSeekCompleteListener mOnSeekCompleteListener;

    private int mDecodeType = -1;
    //默认是回放
    private int mPlayerType = 0;


    public ZMAudioPlayer(Context context) {
        mAudioPlayer = new PiliAudioPlayer(context);
    }


    public ZMAudioPlayer build() {
        mAudioPlayer.setDecodeType(mDecodeType);
        mAudioPlayer.setPlayerType(mPlayerType);
        mAudioPlayer.setOnPrepareListener(mOnPrepareListener);
        mAudioPlayer.setOnCompleteListener(mOnCompleteListener);
        mAudioPlayer.setOnErrorListener(mOnErrorListener);
        mAudioPlayer.init();
        return this;
    }

    public ZMAudioPlayer prepareAsync() {
        if (mAudioPlayer != null) {
            mAudioPlayer.prepareAsync();
        }
        return this;
    }

    public void start() {
        if (mAudioPlayer != null) {
            mAudioPlayer.start();
        }
    }

    public void pause() {
        if (mAudioPlayer != null) {
            mAudioPlayer.pause();
        }
    }

    public void stop() {
        if (mAudioPlayer != null) {
            mAudioPlayer.stop();
        }
    }

    public ZMAudioPlayer seekTo(long time) {
        if (mAudioPlayer != null) {
            mAudioPlayer.seekTo(time);
        }
        return this;
    }

    public void release() {
        if (mAudioPlayer != null) {
            mAudioPlayer.release();
        }
    }

    public void reset() {
        if (mAudioPlayer != null) {
            mAudioPlayer.reset();
        }
    }

    public ZMAudioPlayer setWakeMode(int mode) {
        if (mAudioPlayer != null) {
            mAudioPlayer.setWakeMode(mode);
        }
        return this;
    }

    public ZMAudioPlayer setVolume(float l, float r) {
        if (mAudioPlayer != null) {
            mAudioPlayer.setVolume(l, r);
        }
        return this;
    }

    public ZMAudioPlayer setPlayerType(int type) {
        this.mPlayerType = type;
        return this;
    }

    public ZMAudioPlayer setDecodeType(int type) {
        this.mDecodeType = type;
        return this;
    }

    public ZMAudioPlayer setPath(String path) throws IOException {
        if (mAudioPlayer != null) {
            mAudioPlayer.setPath(path);
        } else {
            Log.e("ZMAudioPlayer", "mAudioPlayer is null , 'setPath() ' is useless , just ignore ,");
        }
        return this;
    }

    public ZMAudioPlayer setOnPrepareListener(PlayerCallback.OnPrepareListener listener) {
        this.mOnPrepareListener = listener;
        if (mAudioPlayer != null) {
            mAudioPlayer.setOnPrepareListener(listener);
        }
        return this;
    }

    public ZMAudioPlayer setOnErrorListener(PlayerCallback.OnErrorListener listener) {
        this.mOnErrorListener = listener;
        if (mAudioPlayer != null) {
            mAudioPlayer.setOnErrorListener(listener);
        }
        return this;
    }

    public ZMAudioPlayer setOnCompleteListener(PlayerCallback.OnCompleteListener listener) {
        this.mOnCompleteListener = listener;
        if (mAudioPlayer != null) {
            mAudioPlayer.setOnCompleteListener(listener);
        }
        return this;
    }

    public ZMAudioPlayer setOnInfoListener(PlayerCallback.OnInfoListener listener) {
        this.mOnInfoListener = listener;
        if(mAudioPlayer != null){
            mAudioPlayer.setOnInfoListener(listener);
        }
        return this;
    }

    public ZMAudioPlayer setOnBufferingUpdateListener(PlayerCallback.OnBufferingUpdateListener listener) {
        this.mOnBufferingUpdateListener = listener;
        if(mAudioPlayer != null){
            mAudioPlayer.setOnBufferingUpdateListener(listener);
        }
        return this;
    }

    public ZMAudioPlayer setOnSeekCompleteListener(PlayerCallback.OnSeekCompleteListener listener) {
        this.mOnSeekCompleteListener = listener;
        if (mAudioPlayer != null) {
            mAudioPlayer.setOnSeekCompleteListener(listener);
        }
        return this;
    }

}
