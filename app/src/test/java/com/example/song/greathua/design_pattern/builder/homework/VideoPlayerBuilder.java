package com.example.song.greathua.design_pattern.builder.homework;

/**
 * Created by PVer on 2017/4/23.
 */

public class VideoPlayerBuilder implements IPlayerBuilder {
    IPlayer player = new VideoPlayer();
    @Override
    public void setType(String type) {
        player.setDecodeType(type);
    }

    @Override
    public void setPath(String path) {
        player.setPath(path);
    }

    @Override
    public void setAnimation(String animation) {
        player.setPlayingAnimation(animation);
    }

    @Override
    public IPlayer build() {
        return player;
    }
}
