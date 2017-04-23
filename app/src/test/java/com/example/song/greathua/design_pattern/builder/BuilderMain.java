package com.example.song.greathua.design_pattern.builder;

import org.junit.Test;

/**
 * Created by PVer on 2017/4/22.
 *
 */

public class BuilderMain {

    @Test
    public void main() {
        ComputerBuilder builder = new MacbookBuilder();
        Director director = new Director(builder);
        director.Construct("英特尔主板","Retina显示器");
        System.out.println("Computer Info : "+ builder.build().toString());
    }
}
