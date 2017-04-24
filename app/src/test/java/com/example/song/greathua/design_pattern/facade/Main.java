package com.example.song.greathua.design_pattern.facade;

import org.junit.Test;

/**
 * Created by PVer on 2017/4/24.
 * 外观模式
 *
 * 模拟一个自动驾驶汽车
 */

public class Main {
    @Test
   public void main(){
        AutoCar autoCar = AutoCar.getInstance();
        autoCar.autoDrive();
   }
}
