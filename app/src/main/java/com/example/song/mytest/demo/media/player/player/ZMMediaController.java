package com.example.song.mytest.demo.media.player.player;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.song.R;

import java.lang.ref.WeakReference;
import java.util.Locale;


/**
 * Created by zz on 2017/4/17.
 * <p>
 * MediaController
 */

public class ZMMediaController extends LinearLayout implements IMediaController {

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
    private long mDefShowTime = 3000;
    //是否是显示的状态
    private boolean mIsShowing = false;
    //播放类型 0 回放 1 直播
    private int mCurPlayerType = 1;

    private boolean mDragging;

    private static final int MSG_HIDE_CONTROLLER = 0x01;
    private static final int MSG_SHOW_CONTROLLER = 0x02;

    private Handler mHandler = new UpdateHandler(this);

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
                    controller.setProgress();
                    if (!controller.mDragging && controller.isShowing()) {
                        sendEmptyMessageDelayed(MSG_SHOW_CONTROLLER, 1000 - (controller.mCurPlayerType % 1000));
                        controller.updatePlayBtn();
                    }
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
    }

    @Override
    public void onMediaControllerSet(IMediaPlayer player) {
        this.mPlayer = player;
        //初始化时获取一次播放类型，以后都是在reset的时候获取
        this.mCurPlayerType = mPlayer.getPlayerType();
        buildView();
    }


    private void buildView() {
        this.setBackgroundResource(R.drawable.bg_media_controller);
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);
        inflate(mContext, R.layout.live_media_controller, this);
        initViews();
    }

    @Override
    public void show() {
        show(0);
    }

    @Override
    public void show(long time) {
        if (mPlayer == null) {
            throw new NullPointerException("IMediaPlayer in mediaController is null , have u invoked method ：IMediaPlayer#setMediaController ?");
        }
        //隐藏状态下才显示
        if (!mIsShowing) {
            setViewEnable();
            setVisibility(VISIBLE);
            updateViews();
            mHandler.sendEmptyMessage(MSG_SHOW_CONTROLLER);
            if (time != 0) {
                mHandler.removeMessages(MSG_HIDE_CONTROLLER);
                mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROLLER, time);
            }
        }
    }


    @Override
    public boolean isShowing() {
        return mIsShowing;
    }

    @Override
    public void reset() {


    }

    private void initViews() {
        mIvPlay = (ImageView) findViewById(R.id.btn_play);
        mTvTimeCur = (TextView) findViewById(R.id.text_current);
        mTvTimeTotal = (TextView) findViewById(R.id.text_total);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
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

    /**
     * 更新view
     * 包括播放按钮背景、时间更新、进度更新
     */
    private void updateViews() {
        //回放
        if (mCurPlayerType == 0) {
            mHandler.sendEmptyMessage(MSG_SHOW_CONTROLLER);
        }
        updatePlayBtn();
    }

    private void updatePlayBtn() {
        mIvPlay.setImageResource(mPlayer.isPlaying() ? R.drawable.btn_media_pause : R.drawable.btn_media_play);
    }

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

    private void setClickListeners(){
        mPlayer.
    }
}
