package com.example.song.homework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.song.base.BaseActivity;
import com.example.song.homework.bean.HashMap;

/**
 * <p> Created by 宋华 on 2017/11/16.
 */
public class HashMapActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //没有界面， 看日志
        testHashMap();
    }

    private void testHashMap() {
        HashMap<String, String> data = new HashMap<>(8);
        //测试put和get
        System.out.println("测试put和get:");
        data.put("1", "1");
        data.put("2", "2");
        data.put("3", "3");
        data.put("4", "4");
        data.put("4", "4");
        data.put("4", "4");
        System.out.println("size = " + data.size());
        for (int i = 1; i <= 4; i++) {
            System.out.println("hashMap: key=" + i + "时,   value=" + data.get(i + ""));
        }
        //===========================================
        //测试remove和set
        System.out.println("\n\n测试remove和set:");
        data.remove("1");
        System.out.println("hashMap: key=1时,   value=" + data.get("1"));
        data.put("1", "1");
        System.out.println("hashMap: key=1时,   value=" + data.get("1"));
        data.set("1", "-1");
        System.out.println("hashMap: key=1时,   value=" + data.get("1"));
        //=============================================================
        //测试扩容
        System.out.println("\n\n测试扩容:");
        data.put("5", "5");
        data.put("6", "6");
        data.put("7", "7");
        data.put("8", "8");
        data.put("9", "9");

        int size = data.size();
        System.out.println("size = " + size);
        for (int i = 1; i <= size; i++) {
            System.out.println("hashMap: key=" + i + "时,   value=" + data.get(i + ""));
        }
        //==========================================================
        //测试put null值
        System.out.println("\n\n测试key为null :");
        data.put(null, "null");
        System.out.println("null 值为："+data.get(null));
        data.put(null, "null,null");
        System.out.println("null 值为："+data.get(null));
        System.out.println("put null 后的Size =="+data.size());

    }
}
