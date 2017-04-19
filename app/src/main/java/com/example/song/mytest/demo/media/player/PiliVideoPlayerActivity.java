package com.example.song.mytest.demo.media.player;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.commonlibrary.util.ToastUtil;
import com.example.song.R;
import com.example.song.base.CommonActivity;
import com.example.song.mytest.demo.media.player.callback.PlayerCallback;
import com.example.song.mytest.demo.media.player.player.ZMMediaController;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import java.io.IOException;


/**
 * Created by zz on 2017/4/5.
 */

public class PiliVideoPlayerActivity extends CommonActivity {
    private ZMVideoPlayer mVideoPlayer;
    private ZMMediaController mMediaController;
    private boolean isControllerShowing = false;
    private TextView mTvSwitchOrientation;
    private TextView mTvSwitchRatio;
    private RelativeLayout mRoot;
    private int mCurrentDegree = 0;
    private int mDisplayAspectRatio = PLVideoTextureView.ASPECT_RATIO_FIT_PARENT; //default

    private String mVideoPath = "http://pili-static.live.zm.gaiay.cn/recordings/z1.gaiay-pro.58e49691d425e14640259fcc/1491375768.1491375856.m3u8";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pili_video_player);
        initView();
        ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
        ImageView cover = (ImageView) findViewById(R.id.cover_view);
        try {
            mVideoPlayer.setDecodeType(AVOptions.MEDIA_CODEC_SW_DECODE)
                    .setPlayerType(0)
                    .setOnPrepareListener(new PlayerCallback.OnPrepareListener() {
                        @Override
                        public void onPrepared() {
                            ToastUtil.showMessage("prepared ");
                        }
                    })
                    .setOnErrorListener(new PlayerCallback.OnErrorListener() {
                        @Override
                        public boolean onError(int errorCode) {
                            ToastUtil.showMessage("onError code ==" + errorCode);
                            return true;
                        }
                    })
                    .setOnCompleteListener(new PlayerCallback.OnCompleteListener() {
                        @Override
                        public void onComplete() {
                            ToastUtil.showMessage("complete");
                        }
                    })
                    .setBufferingView(loading)
                    .setCoverView(cover)
                    .setVideoPath(mVideoPath)
                    .build().start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        mRoot = (RelativeLayout) findViewById(R.id.root);
        mVideoPlayer = (ZMVideoPlayer) findViewById(R.id.player);
        mTvSwitchOrientation = (TextView) findViewById(R.id.switch_orientation);
        mTvSwitchOrientation.setOnClickListener(this);
        mTvSwitchRatio = (TextView) findViewById(R.id.switch_ratio);
        mTvSwitchRatio.setOnClickListener(this);
        mTvSwitchOrientation.setText("旋转0°");
        mMediaController = (ZMMediaController) findViewById(R.id.controller);
        mVideoPlayer.setMediaController(mMediaController);
        mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaController.isShowing()) {
                    mMediaController.hide();
                } else {
                    mMediaController.show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.switch_orientation:
                mCurrentDegree = (mCurrentDegree + 90) % 360;
                mVideoPlayer.setDisplayOrientation(mCurrentDegree);
                mTvSwitchOrientation.setText("旋转" + mCurrentDegree + "°");
                break;

            case R.id.switch_ratio:
                switchRatio();
                break;
        }
    }

    public void switchRatio() {
        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mVideoPlayer.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mVideoPlayer.getDisplayAspectRatio()) {
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
        mVideoPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mVideoPlayer.start();
    }

    @Override
    protected void onDestroy() {
        mVideoPlayer.stop();
        super.onDestroy();
    }
}
