package com.example.song.greathua.design_pattern.builder.homework;

/**
 * Created by PVer on 2017/4/23.
 *
 */

public interface IPlayer {
    void play();
    void stop();
    void setPath(String path);
    void setDecodeType(String type);
    void setPlayingAnimation(String animation );
}
