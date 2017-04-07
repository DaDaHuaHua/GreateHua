package com.example.song.greathua.mytest.demo.media.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.commonlibrary.util.ToastUtil;
import com.example.song.greathua.R;
import com.example.song.greathua.base.CommonActivity;
import com.example.song.greathua.mytest.demo.media.player.video.ZMVideoPlayer;
import com.example.song.greathua.mytest.demo.media.player.video.callback.PlayerCallback;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zz on 2017/4/5.
 *
 */

public class PiliVideoPlayerActivity extends CommonActivity {
    @BindView(R.id.player)
     ZMVideoPlayer mVideoPlayer;
    @BindView(R.id.switch_orientation)
    TextView mTvSwitchOrientation;
    @BindView(R.id.switch_ratio)
    TextView mTvSwitchRatio;
    private int mCurrentDegree = 0;
    private int mDisplayAspectRatio = PLVideoTextureView.ASPECT_RATIO_FIT_PARENT; //default

    private String mVideoPath = "http://pili-static.live.zm.gaiay.cn/recordings/z1.gaiay-pro.58e49691d425e14640259fcc/1491375768.1491375856.m3u8";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pili_video_player);
        initView();
        mVideoPlayer.setDecodeType(AVOptions.MEDIA_CODEC_AUTO)
                .setDisplayOrientation(0)
                .setVideoType(0)
                .setOnPrepareListener(new PlayerCallback.OnPrepareListener() {
                    @Override
                    public void onPrepare() {
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
                }).setVideoPath(mVideoPath).setDisplayOrientation(90).build().start();
    }

    private void initView(){
        mTvSwitchOrientation.setText("旋转0°");
    }

    @OnClick({R.id.switch_orientation ,R.id.switch_ratio})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.switch_orientation :
                mCurrentDegree = (mCurrentDegree += 90 )> 270 ?  0 : mCurrentDegree ;
                mVideoPlayer.setDisplayOrientation(mCurrentDegree);
                mTvSwitchOrientation.setText("旋转"+mCurrentDegree+"°");
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
