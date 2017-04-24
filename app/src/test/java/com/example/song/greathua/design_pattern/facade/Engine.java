package com.example.song.greathua.design_pattern.facade;

/**
 * Created by PVer on 2017/4/24.
 * 发动机 -- Module 模块角色
 */

public class Engine {


    public void startEngine() {
        System.out.println("发动机启动了，轰轰轰...");
    }

    public void run() {
        System.out.println("汽车开始向前走了...");
    }
}
