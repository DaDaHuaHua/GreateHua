package com.example.song.greathua.design_pattern.facade;

/**
 * Created by PVer on 2017/4/24.
 * 点火器 -- Module 角色
 */

public class Igniter {

    public void ignite(Engine engine) {
        System.out.println("点火器启动了， 即将启动发动机...");
        engine.startEngine();
    }
}
