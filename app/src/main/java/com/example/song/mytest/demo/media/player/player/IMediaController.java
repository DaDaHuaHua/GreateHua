package com.example.song.mytest.demo.media.player.player;

import android.support.annotation.LayoutRes;

/**
 * Created by zz on 2017/4/17.
 * MediaController
 */

 interface IMediaController {

    @LayoutRes int getControllerLayout();

    void setPlayer(IMediaPlayer player);

}
