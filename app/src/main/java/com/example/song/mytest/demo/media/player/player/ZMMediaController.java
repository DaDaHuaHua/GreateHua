package com.example.song.mytest.demo.media.player.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.song.R;

/**
 * Created by zz on 2017/4/17.
 * <p>
 * MediaController
 */

public class ZMMediaController extends RelativeLayout implements IMediaController {

    private IMediaPlayer mPlayer;
    private long mCurrentTime;
    private Context mContext;

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
    public void setPlayer(@NonNull IMediaPlayer player) {
        this.mPlayer = player;
    }

    @Override
    public int getControllerLayout() {
        return R.layout.live_media_controller;
    }

    /**
     *
     */
    public void build() {
        if (mPlayer == null) {
            throw new NullPointerException("u must setPlayer before invoke 'build()'");
        }
        if (getControllerLayout() <= 0) {
            throw new NullPointerException("u should invoke getControllerLayout() for inflating a specific view as mediaController");
        }
        View content = ((LayoutInflater) (mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(getControllerLayout(), null);
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(content, params);
    }
}
