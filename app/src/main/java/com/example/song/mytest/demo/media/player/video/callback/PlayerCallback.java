package com.example.song.mytest.demo.media.player.video.callback;

/**
 * Created by zz on 2017/4/6.
 */

public class PlayerCallback {
   public interface OnPrepareListener{
        void onPrepare();
    }
    public interface  OnErrorListener{
        boolean onError(int errorCode);
    }
    public interface  OnCompleteListener{
        void onComplete();
    }
}
