package com.example.song.demo.media.player.player;


import com.example.song.demo.media.player.player.IMediaPlayer;

/**
 * Created by zz on 2017/4/17.
 * MediaController
 */

public interface IMediaController extends IMediaPlayer.OnControllerSetListener {



    void reset();

    void show();

    void show(long time);

    void hide();

    boolean isShowing();

    void setOnShownListener(OnShownListener listener);

    void setOnHiddenListener(OnHiddenListener listener);


    interface OnShownListener{
        void onShown();
    }

    interface OnHiddenListener{
        void onHidden();
    }

    interface OnPlayStateListener{
        void onPlay();

        void onPause();
    }


}
