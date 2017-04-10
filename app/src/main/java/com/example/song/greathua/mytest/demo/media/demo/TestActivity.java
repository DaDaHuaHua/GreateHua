package com.example.song.greathua.mytest.demo.media.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commonlibrary.util.ToastUtil;
import com.example.song.greathua.R;
import com.example.song.greathua.base.CommonActivity;
import com.example.song.greathua.mytest.demo.media.player.video.MediaController;
import com.example.song.greathua.mytest.demo.media.player.video.ZMVideoPlayer;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;

/**
 * Created by zz on 2017/4/10.
 */

public class TestActivity extends CommonActivity {

    private PLVideoTextureView mPlayer;
    private int mVideoType = 0;
    private MediaController mediaController;
    private String mVideoPath = "http://pili-static.live.zm.gaiay.cn/recordings/z1.gaiay-pro.58e49691d425e14640259fcc/1491375768.1491375856.m3u8";

    private TextView mTvSwitchOrientation;
    private TextView mTvSwitchRatio;
    private int mCurrentDegree = 0;
    private int mDisplayAspectRatio = PLVideoTextureView.ASPECT_RATIO_FIT_PARENT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pili_video_player);
        mTvSwitchOrientation = (TextView) findViewById(R.id.switch_orientation);
        mTvSwitchOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDegree = (mCurrentDegree + 90) % 360;
                mPlayer.setDisplayOrientation(mCurrentDegree);
            }
        });
        mTvSwitchRatio = (TextView) findViewById(R.id.switch_ratio);
        mTvSwitchRatio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchRatio();
            }
        });
        // mPlayer = (PLVideoTextureView) findViewById(R.id.player);
        mPlayer = new PLVideoTextureView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPlayer.setLayoutParams(params);
        RelativeLayout content = (RelativeLayout) findViewById(R.id.layout_content);
        content.addView(mPlayer);


        mPlayer.setOnPreparedListener(new PLMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(PLMediaPlayer plMediaPlayer) {
                ToastUtil.showMessage("就绪！");
            }
        });
        mPlayer.setOnErrorListener(new PLMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(PLMediaPlayer plMediaPlayer, int i) {
                ToastUtil.showMessage("onError code ==" + i);
                return true;
            }
        });
        mPlayer.setOnCompletionListener(new PLMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(PLMediaPlayer plMediaPlayer) {
                ToastUtil.showMessage("complete");
            }
        });
        mediaController = new MediaController(this, false, mVideoType == 1);
        mPlayer.setMediaController(mediaController);
        mPlayer.setVideoPath(mVideoPath);
        setOptions();
        mPlayer.start();
    }

    private void setOptions() {
        AVOptions options = new AVOptions();
        // 解码方式:
        // codec＝AVOptions.MEDIA_CODEC_HW_DECODE，硬解
        // codec=AVOptions.MEDIA_CODEC_SW_DECODE, 软解
        // codec=AVOptions.MEDIA_CODEC_AUTO, 硬解优先，失败后自动切换到软解
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
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

    public void switchRatio() {
        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mPlayer.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mPlayer.getDisplayAspectRatio()) {
            case PLVideoTextureView.ASPECT_RATIO_ORIGIN:
                ToastUtil.showMessage("Origin mode");
                break;
            case PLVideoTextureView.ASPECT_RATIO_FIT_PARENT:
                ToastUtil.showMessage("Fit parent !");
                break;
            case PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT:
                ToastUtil.showMessage("Paved parent !");
                break;
            case PLVideoTextureView.ASPECT_RATIO_16_9:
                ToastUtil.showMessage("16 : 9 !");
                break;
            case PLVideoTextureView.ASPECT_RATIO_4_3:
                ToastUtil.showMessage("4 : 3 !");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPlayer.start();
    }

    @Override
    protected void onDestroy() {
        mPlayer.stopPlayback();
        super.onDestroy();
    }
}
