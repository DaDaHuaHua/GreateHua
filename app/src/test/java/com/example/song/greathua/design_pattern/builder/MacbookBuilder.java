package com.example.song.greathua.design_pattern.builder;

/**
 * Created by PVer on 2017/4/23.
 */

public class MacbookBuilder extends ComputerBuilder {
    Computer mComputer = new MacBook();

    @Override
    public void buildBoard(String board) {
        mComputer.setBoard(board);
    }

    @Override
    public void buildDisplay(String display) {
        mComputer.setDisplay(display);
    }

    @Override
    public void buildOS() {
        mComputer.setOs();
    }

    @Override
    public Computer build() {
        return mComputer;
    }
}
