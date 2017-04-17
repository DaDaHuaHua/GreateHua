package com.example.song.mytest.demo.media.player;

import android.content.Context;

import com.example.song.mytest.demo.media.player.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.player.IAudioPlayer;
import com.example.song.mytest.demo.media.player.player.playerimpl.PiliAudioPlayer;

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

    public ZMAudioPlayer(Context context) {
        mAudioPlayer = new PiliAudioPlayer(context);
    }


    /**
     * 配置播放器后需要调用此方法创建真正的播放器实现
     */
    public ZMAudioPlayer build() throws IOException {
        mAudioPlayer.init();
        return this;
    }

    public void prepareAsync() {
        mAudioPlayer.prepareAsync();
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

    public void release() {
        mAudioPlayer.release();
    }

    public void reset() {
        mAudioPlayer.reset();
    }

    /**
     *
     *  mode 设置wakeLock模式
     */
    public ZMAudioPlayer setWakeMode(int mode) {
        mAudioPlayer.setWakeMode(mode);
        return this;
    }

    /**
     * 设置当前播放的是否为在线直播，如果是，底层会有一些播放优化
     *
     * @param type 0 回放 1直播
     * @return ZMVideoPlayer
     */
    public ZMAudioPlayer setPlayerType(int type) {
        mAudioPlayer.setPlayerType(type);
        return this;
    }

    /**
     * 设置解码方式
     *
     * @param type 0软解码 1硬解码 2自动解码
     * @return ZMVideoPlayer
     */
    public ZMAudioPlayer setDecodeType(int type) {
        mAudioPlayer.setDecodeType(type);
        return this;
    }

    /**
     * 设置播放路径
     */
    public ZMAudioPlayer setPath(String path) throws IOException {
        mAudioPlayer.setPath(path);
        return this;
    }

    public ZMAudioPlayer setOnPrepareListener(PlayerCallback.OnPrepareListener listener) {
        mAudioPlayer.setOnPrepareListener(listener);
        return this;
    }

    public ZMAudioPlayer setOnErrorListener(PlayerCallback.OnErrorListener listener) {
        mAudioPlayer.setOnErrorListener(listener);
        return this;
    }

    public ZMAudioPlayer setOnCompleteListener(PlayerCallback.OnCompleteListener listener) {
        mAudioPlayer.setOnCompleteListener(listener);
        return this;
    }

    public ZMAudioPlayer setOnInfoListener(PlayerCallback.OnInfoListener listener) {
        mAudioPlayer.setOnInfoListener(listener);
        return this;
    }

    public ZMAudioPlayer setOnBufferingUpdateListener(PlayerCallback.OnBufferingUpdateListener listener) {
        mAudioPlayer.setOnBufferingUpdateListener(listener);
        return this;
    }

    public ZMAudioPlayer setOnSeekCompleteListener(PlayerCallback.OnSeekCompleteListener listener) {
        mAudioPlayer.setOnSeekCompleteListener(listener);
        return this;
    }

}
