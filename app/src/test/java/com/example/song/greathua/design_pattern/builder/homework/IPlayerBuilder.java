package com.example.song.greathua.design_pattern.builder.homework;

/**
 * Created by PVer on 2017/4/23.
 */

public interface IPlayerBuilder {

    void setType(String type);

    void setPath(String path);

    void setAnimation(String animation);
    IPlayer build();
}
