package com.example.song.greathua.design_pattern.builder;

/**
 * Created by PVer on 2017/4/22.
 * builder 的抽象类
 */

public abstract class ComputerBuilder {

    public abstract void buildBoard(String board);

    public abstract void buildDisplay(String display);

    public abstract void buildOS();

    public abstract Computer build();

}
