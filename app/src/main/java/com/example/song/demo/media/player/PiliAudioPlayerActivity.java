package com.example.song.demo.media.player;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.example.commonlibrary.util.ToastUtil;
import com.example.song.R;
import com.example.song.base.CommonActivity;
import com.example.song.demo.media.player.callback.PlayerCallback;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;

import butterknife.BindView;

/**
 * Created by zz on 2017/4/13.
 * 测试ZMAudioPlayer
 */

public class PiliAudioPlayerActivity extends CommonActivity {
    private String TAG = "PiliAudioPlayerTAG";
    private ZMAudioPlayer mPlayer;
    private boolean mIsStopped = false;
    private String mPath = "http://pili-static.live.zm.gaiay.cn/recordings/z1.gaiay-pro.58eedc8320a05d234225a77e/1492049041.1492049055.m3u8";

    @BindView(R.id.loading_view)
    View mLoadingView;

    TelephonyManager mTelephonyManager;
    PhoneStateListener mPhoneStateListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pili_audio_player);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        prepare();
        startTelephonyListener();

    }


    public void prepare() {
        if (mPlayer == null) {
            mPlayer = new ZMAudioPlayer(this);
        }
        try {
            mPlayer.setDecodeType(0)
                    .setPlayerType(0)
                    .setOnErrorListener(new PlayerCallback.OnErrorListener() {
                        @Override
                        public boolean onError(int errorCode) {
                            return PiliAudioPlayerActivity.this.onError(errorCode);
                        }
                    })
                    .setOnCompleteListener(new PlayerCallback.OnCompleteListener() {
                        @Override
                        public void onComplete() {
                            ToastUtil.showMessage("Play Completed !");
                        }
                    })
                    .setOnPrepareListener(new PlayerCallback.OnPrepareListener() {
                        @Override
                        public void onPrepared() {
                            ToastUtil.showMessage("On Prepared !");
                            mPlayer.start();
                            mIsStopped = false;
                        }
                    })
                    .setOnInfoListener(new PlayerCallback.OnInfoListener() {
                        @Override
                        public void onInfo(int what, int extra) {

                        }
                    })
                    .setOnSeekCompleteListener(new PlayerCallback.OnSeekCompleteListener() {
                        @Override
                        public void onSeekComplete() {

                        }
                    })
                    .setOnBufferingUpdateListener(new PlayerCallback.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(int percent) {

                        }
                    })
                    .build()
                    .setPath(mPath)
                    .prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void release() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private boolean onError(int errorCode) {
        boolean isNeedReconnect = false;
        Log.e(TAG, "Error happened, errorCode = " + errorCode);
        switch (errorCode) {
            case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                ToastUtil.showMessage("Invalid URL !");
                break;
            case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                ToastUtil.showMessage("404 resource not found !");
                break;
            case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                ToastUtil.showMessage("Connection refused !");
                break;
            case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                ToastUtil.showMessage("Connection timeout !");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                ToastUtil.showMessage("Empty playlist !");
                break;
            case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                ToastUtil.showMessage("Stream disconnected !");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                ToastUtil.showMessage("Network IO Error !");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                ToastUtil.showMessage("Unauthorized Error !");
                break;
            case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                ToastUtil.showMessage("Prepare timeout !");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                ToastUtil.showMessage("Read frame timeout !");
                isNeedReconnect = true;
                break;
            case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                break;
            default:
                ToastUtil.showMessage("unknown error !");
                break;
        }
        return true;
    }

    private void startTelephonyListener() {
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyManager == null) {
            Log.e(TAG, "Failed to initialize TelephonyManager!!!");
            return;
        }

        mPhoneStateListener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                // TODO Auto-generated method stub
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.d(TAG, "PhoneStateListener: CALL_STATE_IDLE");
                        if (mPlayer != null) {
                            mPlayer.start();
                        }
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.d(TAG, "PhoneStateListener: CALL_STATE_OFFHOOK");
                        if (mPlayer != null) {
                            mPlayer.pause();
                        }
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.d(TAG, "PhoneStateListener: CALL_STATE_RINGING: " + incomingNumber);
                        break;
                }
            }
        };

        try {
            mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopTelephonyListener() {
        if (mTelephonyManager != null && mPhoneStateListener != null) {
            mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
            mTelephonyManager = null;
            mPhoneStateListener = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTelephonyListener();
        release();
    }

    public void onClickPlay(View v) {
        if (mIsStopped) {
            prepare();
        } else {
            mPlayer.start();
        }
    }

    public void onClickPause(View v) {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    public void onClickResume(View v) {
        if (mPlayer != null) {
            mPlayer.start();
        }
    }

    public void onClickStop(View v) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }
        mIsStopped = true;
        mPlayer = null;
    }

    private float volumeL = 0.0f;
    private float volumeR = 0.0f;



}
