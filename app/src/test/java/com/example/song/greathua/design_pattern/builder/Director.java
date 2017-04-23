package com.example.song.greathua.design_pattern.builder;

/**
 * Created by PVer on 2017/4/23.
 */

public class Director {
    ComputerBuilder mBuilder = null;

    public Director(ComputerBuilder builder){
        mBuilder = builder;
    }

    public void Construct(String board ,String display){
        mBuilder.buildBoard(board);
        mBuilder.buildDisplay(display);
        mBuilder.buildOS();
    }
}
