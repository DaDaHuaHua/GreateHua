package com.example.song.greathua.design_pattern.builder.homework;

/**
 * Created by PVer on 2017/4/23.
 */

public class AudioPlayer implements IPlayer {
    private String mPath;
    private String mDecodeType;
    private String mAnimation;

    AudioPlayer() {
    }

    @Override
    public void play() {
        System.out.println("音频播放中...动画显示：" + mAnimation);
    }

    @Override
    public void stop() {
        System.out.println("音频已经停止...");
    }

    @Override
    public void setPath(String path) {
        this.mPath = path;
    }

    @Override
    public void setDecodeType(String type) {
        this.mDecodeType = type;
    }

    @Override
    public void setPlayingAnimation(String animation) {
        this.mAnimation = animation;
    }

    @Override
    public String toString() {
        return "音频播放器： 解码方式：" + mDecodeType + "  播放动画" + mAnimation + "  播放地址" + mPath;
    }
}
