package com.example.song.mytest.demo.media.player.video.player.playerimpl;

import android.content.Context;
import android.util.Log;

import com.example.song.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.video.player.IAudioPlayer;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;

/**
 * Created by zz on 2017/4/13.
 * 基于PLMediaPlayer实现音频播放器
 */

public class PiliAudioPlayer implements IAudioPlayer {
    private static final String TAG = "PiliAudioPlayerTag";
    private Context mContext;
    private PLMediaPlayer mPlayer;
    //Pili不支持切换
    private int mAudioType = 0;
    private int mDecodeType;
    private AVOptions mAVOptions;
    private PlayerCallback.OnCompleteListener mOnCompleteListener;
    private PlayerCallback.OnPrepareListener mOnPrepareListener;
    private PlayerCallback.OnErrorListener mOnErrorListener;

    public PiliAudioPlayer(Context context) {
        this.mContext = context;
    }

    private void buildOptions() {
        mAVOptions = new AVOptions();
        mAVOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        mAVOptions.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        mAVOptions.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        mAVOptions.setInteger(AVOptions.KEY_LIVE_STREAMING, mAudioType);
        if (mAudioType == 1) {
            mAVOptions.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }
        mAVOptions.setInteger(AVOptions.KEY_MEDIACODEC, mDecodeType);
        mAVOptions.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
    }

    @Override
    public void init() {
        buildOptions();
        mPlayer = new PLMediaPlayer(mContext, mAVOptions);
        mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(PLMediaPlayer plMediaPlayer) {
                if (mOnPrepareListener != null) {
                    mOnPrepareListener.onPrepared();
                }
            }
        });
        mPlayer.setOnErrorListener(new PLMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
                if (mOnErrorListener != null) {
                    return mOnErrorListener.onError(i);
                }
                return false;

            }
        });
        mPlayer.setOnCompletionListener(new PLMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(PLMediaPlayer plMediaPlayer) {
                if (mOnCompleteListener != null) {
                    mOnCompleteListener.onComplete();
                }
            }
        });
        //onInfo()不暴露出去
        mPlayer.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                Log.i(TAG, "OnInfo, what = " + i + ", extra = " + i1);
                return true;
            }
        });
        //onBufferingUpdate() 不暴露出去
        mPlayer.setOnBufferingUpdateListener(new PLMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int i) {
                Log.d(TAG, "onBufferingUpdate: " + i + "%");
            }
        });
        //setOnInfoListener 不暴露出去
        mPlayer.setOnInfoListener(new PLMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(PLMediaPlayer plMediaPlayer, int i, int i1) {
                return false;
            }
        });
    }

    @Override
    public void prepareAsync() {
        mPlayer.prepareAsync();
    }

    @Override
    public void reset() {
        mPlayer.reset();
    }

    @Override
    public void start() {
        mPlayer.start();
    }

    @Override
    public void stop() {
        mPlayer.stop();
        mPlayer.reset();
    }

    @Override
    public void pause() {
        mPlayer.pause();
    }

    @Override
    public void seekTo(long time) {
        mPlayer.seekTo(time);
    }

    @Override
    public void release() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    @Override
    public void setWakeMode( int mode) {
        mPlayer.setWakeMode(mContext.getApplicationContext(),mode);
    }

    @Override
    public void setVolume(float l, float r) {
        mPlayer.setVolume(l, r);
    }

    @Override
    public void setPath(String path) throws IOException {
        mPlayer.setDataSource(path);
    }

    @Override
    public void setPlayerType(int type) {
        //只能设置初始type,不支持动态设置播放类型，放弃优化
        this.mAudioType = type;
    }

    @Override
    public void setDecodeType(int type) {
        this.mDecodeType = type;
    }

    @Override
    public void setOnPrepareListener(PlayerCallback.OnPrepareListener listener) {
        this.mOnPrepareListener = listener;
    }

    @Override
    public void setOnErrorListener(PlayerCallback.OnErrorListener listener) {
        this.mOnErrorListener = listener;
    }

    @Override
    public void setOnCompleteListener(PlayerCallback.OnCompleteListener listener) {
        this.mOnCompleteListener = listener;
    }
}
