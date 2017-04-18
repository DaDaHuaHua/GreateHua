package com.example.song.mytest.demo.media.player.player;

import android.support.annotation.LayoutRes;

/**
 * Created by zz on 2017/4/17.
 * MediaController
 */

public interface IMediaController extends IMediaPlayer.OnControllerSetListener {



    void reset();

    void show();

    void show(long time);

//    void hide();

    boolean isShowing();

//    void setProgressEnabled(boolean enabled);
}
