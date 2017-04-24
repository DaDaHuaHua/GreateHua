package com.example.song.greathua.design_pattern.facade;

/**
 * Created by PVer on 2017/4/24.
 * 汽车 --Facade 外观角色
 *
 * 单例模式，因为一般情况下 外观模式只存在一个外观类
 */

public class AutoCar {
    private static AutoCar mAutoCar;
    private AutoCar() {

    }


    public static AutoCar getInstance() {
        if (mAutoCar == null) {
            mAutoCar = new AutoCar();
        }
        return mAutoCar;
    }

    public void autoDrive(){
        Igniter igniter = new Igniter();
        Engine engine = new Engine();
        SteeringWheel steeringWheel = new SteeringWheel();
        Trumpet trumpet = new Trumpet();
        //点火,同时启动发动机
        igniter.ignite(engine);
        //转弯
        steeringWheel.turn();
        //鸣笛
        trumpet.tweet();
        //走你
        engine.run();
    }
}
