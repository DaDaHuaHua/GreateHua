package com.example.song.demo.views.audioActionView;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.song.R;

/**
 * Created by SongH on 2018/9/15.
 */
public class AudioActionView extends RelativeLayout {
    public AudioActionView(Context context) {
        this(context, null);
    }

    public AudioActionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private ImageView mLeftLevelIv;
    private ImageView mRightLevelIv;
    private ImageView mProgressIv;
    private TextView mLeftExpTv;
    private TextView mActionStateTv;

    private void init(Context context) {
        inflate(context, R.layout.merge_audio_action_view, this);
        mLeftLevelIv = (ImageView) findViewById(R.id.left_level_iv);
        mRightLevelIv = (ImageView) findViewById(R.id.right_level_iv);
        mProgressIv = (ImageView) findViewById(R.id.progress_iv);
        mLeftExpTv = (TextView) findViewById(R.id.exp_left_tv);
        mActionStateTv = (TextView) findViewById(R.id.action_state_tv);

        setProgress(100, 50);
    }

    public void onActionStart(int total, int current) {

    }

    public void showRunningState(int total, int current) {

    }

    public void showEndState(int total, int current) {

    }


    private void setProgress(int total, int current) {
        if (mProgressIv == null) {
            return;
        }
        Drawable foreground = mProgressIv.getForeground();
        if (foreground == null || !(foreground instanceof ClipDrawable)) {
            return;
        }
        int level;
        if (total == 0 || current == 0) {
            level = 0;
        } else {
            level = 10_000 * current / total;
        }
        foreground.setLevel(level);
    }

    public static void showAndHiddenAnimation(final View view, int state, long duration) {
        float start = 0f;
        float end = 0f;
        if (state == View.VISIBLE) {
            end = 1f;
            view.setVisibility(View.VISIBLE);
        } else if (state == View.GONE) {
            start = 1f;
            view.setVisibility(View.INVISIBLE);
        }
        AlphaAnimation animation = new AlphaAnimation(start, end);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }
        });
        view.setAnimation(animation);
        animation.start();
    }
}
}
