package com.example.song.demo.media.player.player.playerimpl;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.song.R;
import com.example.song.demo.media.player.callback.PlayerCallback;
import com.example.song.demo.media.player.player.IMediaController;
import com.example.song.demo.media.player.player.IMediaPlayer;

import java.lang.ref.WeakReference;
import java.util.Locale;


/**
 * Created by zz on 2017/4/17.
 * <p>
 * MediaController
 */

public class ZMMediaController extends LinearLayout implements IMediaController, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private ImageView mIvPlay;
    private TextView mTvTimeCur;
    private TextView mTvTimeTotal;
    private SeekBar mSeekBar;

    private IMediaPlayer mPlayer;
    //当前播放时长（仅在回放中有效）
    private long mCurrentTime;
    //播放总时长（仅在回放中有效）
    private long mDuration;
    private Context mContext;
    //默认显示时间
    private long mDefShowTime = 5000;
    //是否是显示的状态
    private boolean mIsShowing = false;
    //播放类型 0 回放 1 直播
    private int mCurPlayerType = 1;
    //播放器是否prepared
    private boolean mIsPlayerPrepared;
    private AudioManager mAudioManager;
    private boolean mDragging;

    private static final int MSG_HIDE_CONTROLLER = 0x01;
    private static final int MSG_SHOW_CONTROLLER = 0x02;

    private Handler mHandler = new UpdateHandler(this);

    private OnHiddenListener mOnHiddenListener;
    private OnShownListener mOnShownListener;

    private static class UpdateHandler extends Handler {
        private WeakReference<ZMMediaController> mController;

        private UpdateHandler(ZMMediaController controller) {
            mController = new WeakReference<>(controller);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ZMMediaController controller = mController.get();
            switch (msg.what) {
                case MSG_SHOW_CONTROLLER:
                    if (!controller.mDragging && controller.isShowing()) {
                        sendEmptyMessageDelayed(MSG_SHOW_CONTROLLER, 1000 - (controller.mCurPlayerType % 1000));
                        controller.updatePlayBtn();
                        //只有回放才需要显示进度条和时长
                        if (controller.mPlayer.getPlayerType() == 0) {
                            controller.setProgress();
                        }
                    }
                    break;
                case MSG_HIDE_CONTROLLER:
                    controller.hide();
                    break;

            }
        }
    }

    public ZMMediaController(Context context) {
        this(context, null);
    }

    public ZMMediaController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZMMediaController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        show();
        return true;
    }

    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        show();
        return false;
    }

    // FIXME: 2017/4/20  不执行
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (event.getRepeatCount() == 0 && (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
                || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE || keyCode == KeyEvent.KEYCODE_SPACE)) {
            doPauseResume();
            show();
            if (mIvPlay != null)
                mIvPlay.requestFocus();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP) {
            if (mPlayer.isPlaying()) {
                mPlayer.pause();
                updatePlayBtn();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            hide();
            return true;
        } else {
            show();
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onMediaControllerSet(IMediaPlayer player) {
        this.mPlayer = player;
        //初始化时获取一次播放类型，以后都是在reset的时候获取
        this.mCurPlayerType = mPlayer.getPlayerType();
        buildView();
        mPlayer.setControllerOnPrepareListener(new PlayerCallback.OnPrepareListener() {
            @Override
            public void onPrepared() {
                //只有当播放器Prepared以后才能获取到时长信息
                //show();
                mIsPlayerPrepared = true;
            }
        });
    }


    private void buildView() {
        this.setBackgroundResource(R.drawable.bg_media_controller);
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);
        inflate(mContext, R.layout.live_media_controller, this);
        initViews();
    }

    private void initViews() {
        mIvPlay = (ImageView) findViewById(R.id.btn_play);
        mIvPlay.requestFocus();
        mTvTimeCur = (TextView) findViewById(R.id.text_current);
        mTvTimeTotal = (TextView) findViewById(R.id.text_total);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
        mSeekBar.setMax(1000);
        mIvPlay.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void show() {
        show(mDefShowTime);
    }

    @Override
    public void show(long time) {
        if (mPlayer == null) {
            throw new NullPointerException("IMediaPlayer in mediaController is null , have u invoked method ：IMediaPlayer#setMediaController ?");
        }
        if (!mIsPlayerPrepared) {
            return;
        }
        //隐藏状态下才显示
        if (!mIsShowing) {
            setViewEnable();
            setVisibility(VISIBLE);
            mIsShowing = true;
        }
        if(mOnShownListener != null ){
            mOnShownListener.onShown();
        }
        //更新播放/暂停
        updatePlayBtn();
        mHandler.sendEmptyMessage(MSG_SHOW_CONTROLLER);
        if (time != 0) {
            mHandler.removeMessages(MSG_HIDE_CONTROLLER);
            mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROLLER, time);
        }
    }

    @Override
    public void hide() {
        if (mIsShowing) {
            mHandler.removeMessages(MSG_SHOW_CONTROLLER);
            setVisibility(GONE);
            mIsShowing = false;
            if(mOnHiddenListener != null){
                mOnHiddenListener.onHidden();
            }
        }
    }

    @Override
    public boolean isShowing() {
        return mIsShowing;
    }

    @Override
    public void reset() {
        mSeekBar.setProgress(0);
        mIvPlay.setImageResource(R.drawable.btn_media_play);
        mTvTimeCur.setText(generateTime(0));
        mTvTimeTotal.setText(generateTime(0));
        mCurrentTime = 0;
        mDuration = 0;
        mHandler.removeCallbacksAndMessages(null);
    }


    private void setViewEnable() {
        //观看回放按钮全部显示
        if (mPlayer.getPlayerType() == 0) {
            mIvPlay.setVisibility(VISIBLE);
            mTvTimeCur.setVisibility(VISIBLE);
            mTvTimeTotal.setVisibility(VISIBLE);
            mSeekBar.setVisibility(VISIBLE);
        }
        //观看直播只显示播放/暂停按钮
        else {
            mTvTimeCur.setVisibility(GONE);
            mTvTimeTotal.setVisibility(GONE);
            mSeekBar.setVisibility(GONE);
        }
    }


    private void updatePlayBtn() {
        mIvPlay.setImageResource(mPlayer.isPlaying() ? R.drawable.btn_media_pause : R.drawable.btn_media_play);
    }

    /**
     * 更新进度条和时长
     */
    private void setProgress() {
        if (mPlayer == null || mDragging) {
            return;
        }
        //当前时长
        long position = mCurrentTime = mPlayer.getCurrentPosition();
        //总时长
        long duration = mDuration = mPlayer.getDuration();

        if (duration > 0) {
            long pos = 1000L * position / duration;
            mSeekBar.setProgress((int) pos);
        }
        int percent = mPlayer.getBufferPercentage();
        mSeekBar.setSecondaryProgress(percent * 10);
        mTvTimeTotal.setText(generateTime(mDuration));
        mTvTimeCur.setText(generateTime(mCurrentTime));
    }

    private static String generateTime(long position) {
        int totalSeconds = (int) (position / 1000);

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds).toString();
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds)
                    .toString();
        }
    }


    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePlayBtn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:
                if (mPlayer == null) {
                    return;
                }
                if (!mIsPlayerPrepared) {
                    return;
                }
                doPauseResume();
                show();
                break;
        }
    }

    private Runnable mSeekRunnable;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) return;
        final long newPos = (mDuration * progress) / 1000;
        mHandler.removeCallbacks(mSeekRunnable);
        mSeekRunnable = new Runnable() {
            @Override
            public void run() {
                mPlayer.seekTo(newPos);
            }
        };
        mHandler.postDelayed(mSeekRunnable, 200);
        mTvTimeCur.setText(generateTime(newPos));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mDragging = true;
        show(3600000);
        mHandler.removeMessages(MSG_SHOW_CONTROLLER);
        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mPlayer.seekTo(mDuration * seekBar.getProgress() / 1000);
        show(mDefShowTime);
        mHandler.removeMessages(MSG_SHOW_CONTROLLER);
        mDragging = false;
        mHandler.sendEmptyMessageDelayed(MSG_SHOW_CONTROLLER, 1000);
        mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
    }

    @Override
    public void setOnShownListener(OnShownListener listener) {
        this.mOnShownListener = listener;
    }

    @Override
    public void setOnHiddenListener(OnHiddenListener listener) {
        this.mOnHiddenListener = listener;
    }

    //没有实现快进快退功能
    //快退
//    private void rew() {
//        long pos = mPlayer.getCurrentPosition();
//        pos -= 5000; // milliseconds
//        mPlayer.seekTo(pos);
//        setProgress();
//
//        show(mDefShowTime);
//    }

//    //快进
//    private void ffwd() {
//        long pos = mPlayer.getCurrentPosition();
//        pos += 15000; // milliseconds
//        mPlayer.seekTo(pos);
//        setProgress();
//
//        show(mDefShowTime);
//    }
}
