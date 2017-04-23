package com.example.song.greathua.design_pattern.builder.homework;

/**
 * Created by PVer on 2017/4/23.
 * 负责构建播放器
 */

public class PlayerDirector {

    private IPlayerBuilder mBuilder;

    public PlayerDirector(IPlayerBuilder builder) {
        this.mBuilder = builder;
    }

    public void Construct(String path, String decodeType, String animation) {
        mBuilder.setPath(path);
        mBuilder.setType(decodeType);
        mBuilder.setAnimation(animation);
    }

}
