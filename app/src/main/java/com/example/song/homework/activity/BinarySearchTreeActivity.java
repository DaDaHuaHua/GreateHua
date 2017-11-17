package com.example.song.homework.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.song.base.BaseActivity;
import com.example.song.homework.bean.BinarySearchTree;

/**
 * <p> Created by 宋华 on 2017/11/16.
 */
public class BinarySearchTreeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BinarySearchTree<Integer> data = new BinarySearchTree<>();
        data.add(10);
        data.add(2);
        data.add(3);
        data.add(100);
        data.add(1);
        data.add(50);
        data.add(20);
        data.add(120);
        data.add(40);
        data.add(60);
        data.add(110);
        data.add(150);
        data.add(115);
        data.add(14);
        data.set(14,11);
        data.delete(100);
        data.delete(10);
        data.printMidOrder();
    }
}
