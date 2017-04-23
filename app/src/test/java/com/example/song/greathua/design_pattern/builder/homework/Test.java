package com.example.song.greathua.design_pattern.builder.homework;

/**
 * Created by PVer on 2017/4/23.
 */

public class Test {
    @org.junit.Test
    public void main() {
       // IPlayerBuilder builder = new AudioPlayerBuilder();
        IPlayerBuilder builder = new VideoPlayerBuilder();

        PlayerDirector director = new PlayerDirector(builder);

        //director 中间者，负责控制builder去创建播放器
        //director.Construct("music163.com", "硬解码", "音频动画转转转...");
        director.Construct("aiqiyi.com", "软解码", "视频动画转转转...");

        IPlayer player = builder.build();
        System.out.println(player.toString());
        player.play();
        player.stop();
    }
}
