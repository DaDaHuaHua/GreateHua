package com.example.song.greathua.design_pattern.builder;

/**
 * Created by PVer on 2017/4/22.
 * 电脑基类
 */

public abstract class Computer {
    protected String mBoard;
    protected String mDisplay;
    protected String mOS;

    public Computer() {
    }

    public void setBoard(String board) {
        mBoard = board;
    }

    public void setDisplay(String display) {
        mDisplay = display;
    }

    public abstract void setOs();

    @Override
    public String toString() {
        return "Computer[mBoard= " + mBoard + ",  mDisplay=  " + mDisplay + ",  mOS=" + mOS + "]";
    }
}
