package com.example.song.greathua.mytest.demo.media.player.video.option.optionImpl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.song.greathua.mytest.demo.media.player.video.MediaController;
import com.example.song.greathua.mytest.demo.media.player.video.callback.PlayerCallback;
import com.example.song.greathua.mytest.demo.media.player.video.option.IVideoPlayerOption;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.IMediaController;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;

/**
 * Created by zz on 2017/3/31.
 *
 */

public class PiliPlayerOption implements IVideoPlayerOption {
    private PLVideoTextureView mPlayer;
    private IMediaController mediaController;
    private Context mContext;
    private PlayerCallback.OnPrepareListener mOnPrepareListener;
    private PlayerCallback.OnCompleteListener mOnCompleteListener;
    private PlayerCallback.OnErrorListener mOnErrorListener;
    //播放类型 0回放 1直播
    private int mVideoType;
    //解码类型 0软解码 1硬解码 2自动
    private int mDecodeType;

    private int mCurrentRatio;


    public PiliPlayerOption(Context context) {
        this.mContext = context;
        mPlayer = new PLVideoTextureView(context);
    }

    @Override
    public void init(Context context , int decodeType) {
        this.mDecodeType = decodeType;
        mPlayer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setOptions();
        mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(PLMediaPlayer plMediaPlayer) {
                mOnPrepareListener.onPrepare();
            }
        });
        mPlayer.setOnErrorListener(new PLMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
                return mOnErrorListener.onError(i);
            }
        });
        mPlayer.setOnCompletionListener(new PLMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(PLMediaPlayer plMediaPlayer) {
                mOnCompleteListener.onComplete();
            }
        });
        mediaController = new MediaController(mContext,false,mVideoType ==1 );
        mPlayer.setMediaController(mediaController);

    }

    private void setOptions() {
        AVOptions options = new AVOptions();
        // 解码方式:
        // codec＝AVOptions.MEDIA_CODEC_HW_DECODE，硬解
        // codec=AVOptions.MEDIA_CODEC_SW_DECODE, 软解
        // codec=AVOptions.MEDIA_CODEC_AUTO, 硬解优先，失败后自动切换到软解
        options.setInteger(AVOptions.KEY_MEDIACODEC, mDecodeType);
        // 准备超时时间，包括创建资源、建立连接、请求码流等，单位是 ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 30 * 1000);
        // 读取视频流超时时间，单位是 ms
        // 默认值是：10 * 1000
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 30 * 1000);
        // 播放前最大探测流的字节数，单位是 byte
        // 默认值是：128 * 1024
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // 当前播放的是否为在线直播，如果是，则底层会有一些播放优化
        // 默认值是：0
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mVideoType);
        if (mVideoType == 1) {
            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }
        // 是否自动启动播放，如果设置为 1，则在调用 `prepareAsync` 或者 `setVideoPath` 之后自动启动播放，无需调用 `start()`
        // 默认值是：1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        mPlayer.setAVOptions(options);

    }


    @Override
    public void start() {
        mPlayer.start();
    }

    @Override
    public void stop() {
        mPlayer.stopPlayback();
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
    public void setDisplayOrientation(int degree) {
        boolean b = mPlayer.setDisplayOrientation(degree);
    }

    @Override
    public void setDisplayAspectRatio(int ratio) {
        this.mCurrentRatio = ratio;
        mPlayer.setDisplayAspectRatio(ratio);
    }

    @Override
    public int getDisplayAspectRatio() {
        return  mCurrentRatio;
    }

    @Override
    public void setBufferingView(View v) {
        mPlayer.setBufferingIndicator(v);
    }

    @Override
    public void setCoverView(View v) {
        mPlayer.setCoverView(v);
    }


    @Override
    public View getPlayerLayout() {
        return mPlayer;
    }

    @Override
    public void setVideoPath(String path) {
        mPlayer.setVideoPath(path);
    }

    @Override
    public void setVideoType(int type) {
        this.mVideoType = type;
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
